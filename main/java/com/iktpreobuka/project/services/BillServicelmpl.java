package com.iktpreobuka.project.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.repositories.BillRepository;
import com.iktpreobuka.project.repositories.CategoryRepository;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;

@Service
public class BillServicelmpl implements BillService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private OfferService offerService;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private VoucherService voucherService;

	/*
	 * 2.4 u servisu zaduženom za rad sa računima, napisati metodu koja za
	 * prosleđene datume vraća račune koji se nalaze u datom periodu
	 */

	@Override
	public List<BillEntity> findBillsCreatedBetween(LocalDate startDate, LocalDate endDate) {

		String sql = "SELECT b FROM BillEntity b WHERE b.billCreated BETWEEN :startDate and :endDate";

		Query query = em.createQuery(sql);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);

		List<BillEntity> retVals = query.getResultList();

		return retVals;
	}

	/*
	 * napisati metodu u servisu zaduženom za rad sa računima koja proverava da li
	 * postoje aktivni računi za datu kategoriju
	 */

	@Override
	public Boolean findBillsByCategory(Integer categoryId) {
		List<OfferEntity> offers = offerRepository.findByCategory(categoryRepository.findById(categoryId).get());
		List<BillEntity> bills = new ArrayList<BillEntity>();
		for (OfferEntity offer : offers) {
			if (billRepository.findByOffer(offer) != null) {
				BillEntity bill = billRepository.findByOffer(offer);
				if (bill.getPaymentMade() && bill.getPaymentCanceled() == false)
					bills.add(bill);
			}
			return true;
		}
		return false;
	}

	/*
	 * 2.2 u metodi za dodavanje računa u okviru BillController a potrebno je za
	 * izmenu broja dostupnih/kupljenih ponuda pozvati odgovarajuću metodu servisa
	 * zaduženog za rad sa ponudama
	 */

	public BillEntity createBillWihtOfferAndBuyey(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody BillEntity bill) {
		if (offerRepository.existsById(offerId)) {
			if (userRepository.existsById(buyerId)) {
				UserEntity user = userRepository.findById(buyerId).get();
				OfferEntity offer = offerService.changeAvailableOffers(offerId);
				bill.setUser(user);
				bill.setOffer(offer);
				return billRepository.save(bill);
			}
		}
		return null;
	}

	/*
	 * 2.1 u servisu zaduženom za rad sa ponudama, napisati metodu koja za prosleđen
	 * ID ponude, vrši izmenu broja kupljenih/dostupnih ponuda
	 */

	/* metoda dopunjena da ukoliko je PaymentMAde true automatski pravi vaucer */

	public BillEntity changeBill(@PathVariable Integer id,
			@DateTimeFormat(iso = ISO.DATE) @RequestBody BillEntity changeBill) throws Exception {
		if (billRepository.existsById(id)) {
			BillEntity bill = billRepository.findById(id).get();
			if (changeBill.getPaymentMade() != null) {
				bill.setPaymentMade(changeBill.getPaymentMade());
				if (bill.getPaymentMade())
					voucherService.createVoucherWithBillPaymentMade(bill);
			}
			if (changeBill.getPaymentCanceled() != null) {
				bill.setPaymentCanceled(changeBill.getPaymentCanceled());
				if (bill.getPaymentCanceled()) {
					bill.setOffer(offerService.changeOfferAfterCancelation(bill.getOffer()));
				}
			}
			if (changeBill.getBillCreated() != null)
				bill.setBillCreated(changeBill.getBillCreated());
			return billRepository.save(bill);
		}
		return null;
	}

	@Override
	public Boolean findActiveBills(Integer CategoryId) {
		List<BillEntity> bills = billRepository.findByOfferCategoryId(CategoryId);
		if (billRepository.findByOfferCategoryId(CategoryId) != null)
			for (BillEntity bill : bills) {
				if (!bill.getPaymentMade() && !bill.getPaymentCanceled()) {
					return true;
				}
			}
		return false;
	}

	/*
	 * u okviru servisa zaduženog za rad sa računima napisati metodu koja otkazuje
	 * sve račune odgovarajuće ponude
	 */

	@Override
	public List<BillEntity> cancelBIllsWithExpiredOffer(Integer offerId) {
		List<BillEntity> bills = billRepository.findByOfferId(offerId);
		for (BillEntity bill : bills) {
			bill.setPaymentCanceled(true);
			billRepository.save(bill);
		}
		return bills;
	}
}

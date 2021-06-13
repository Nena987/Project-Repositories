package com.iktpreobuka.project.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.project.entities.VoucherEntity;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JavaMailSender emailSender;

	/* u okviru email servisa kreirati metodu za slanje email poruke */

	@Override
	public void sendVoucherMail(VoucherEntity voucher) throws Exception {
		// TODO Auto-generated method stub

		MimeMessage mail = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setTo(voucher.getUser().getEmail());
		helper.setSubject(voucher.getOffer().getOfferName());
		String text = "<html><body><table" + "style='border:2px solid black'>" + "<tr><td>Buyer</td>" + "<td>Offer</td>"
				+ "<td>Price</td>" + "<td>Expires Date</td></tr>" + "<tr><td>" + voucher.getUser().getFirstName()
				+ voucher.getUser().getLastName() + "</td><td>" + voucher.getOffer().getOfferName() + "</td><td>"
				+ voucher.getOffer().getRegularPrice() + "</td><td>" + voucher.getExpirationDate() + "</td></tr>"
				+ "</table></body></html>";
		helper.setText(text, true);
		emailSender.send(mail);
	}

}
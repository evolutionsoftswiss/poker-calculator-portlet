package ch.evolutionsoft.poker.calculator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "card")
public class Card implements Serializable {

	private static final long serialVersionUID = -5342379094716340230L;

	private static final String[] ranks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K" };

	private static final String[] suits = { "s", "h", "d", "c" };

	private static Map<String, String> validCards;
	static {

		validCards = new HashMap<String, String>();

		for (String rank : ranks) {
			for (String suit : suits) {

				validCards.put((rank + suit).toLowerCase(), rank + suit);
			}
		}
	}
	public static final String EMPTY_IMAGE_PATH = "images/emptycard.png";

	private String value;
	private String imagePath = EMPTY_IMAGE_PATH;

	public Card() {
	}

	public String getValue() {

		return this.value;
	}

	public String getLowerCaseValue() {

		return this.value.toLowerCase();
	}

	public void valueChange(ValueChangeEvent valueChangeEvent) {

		this.setValue(String.valueOf(valueChangeEvent.getNewValue()));
	}

	public void setValue(String value) {

		if (value != null) {
			this.value = validCards.get(value.toLowerCase());
		} else {
			this.value = null;
		}
		if (this.value == null) {
			this.imagePath = EMPTY_IMAGE_PATH;
		} else {

			this.imagePath = "images/card" + value.toLowerCase() + ".jpg";
		}
	}

	public String getImagePath() {

		return this.imagePath;
	}

	public boolean isValid(String value) {

		if (value == null) {
			return false;
		}

		return validCards.containsKey(value.toLowerCase());
	}

	public boolean isValid() {

		if (this.value == null) {
			return true;
		}

		return validCards.containsKey(this.value.toLowerCase());
	}

	public static boolean isValidValue(String value) {

		if (value == null) {
			return true;
		}

		return validCards.containsKey(value.toLowerCase());
	}

	public static List<List<String>> getAllCardValues() {

		List<List<String>> result = new ArrayList<List<String>>();

		for (int suiteIndex = 0; suiteIndex < suits.length; suiteIndex += 2) {

			List<String> currentList = new ArrayList<String>();

			for (String rank : ranks) {
				currentList.add(rank + suits[suiteIndex]);
			}
			for (String rank : ranks) {
				currentList.add(rank + suits[suiteIndex + 1]);
			}

			result.add(currentList);
		}

		return result;
	}

	public void validate(FacesContext context, UIComponent validate, Object value) {

		String cardValue = (String) value;

		if (cardValue == null || cardValue.isEmpty()) {

			((UIInput) validate).setValid(true);
			return;
		}

		boolean isValid = true;

		if (!this.isValid(cardValue)) {

			isValid = false;
			this.setValue(null);
			FacesMessage msg = new FacesMessage("Invalid Card value: " + cardValue);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(validate.getClientId(context), msg);
		}
		((UIInput) validate).setValid(isValid);
	}

	public String toString() {

		return this.value;
	}

	public boolean equals(Object object) {

		if (object == null) {
			return false;
		}

		if (!object.getClass().equals(this.getClass())) {

			return false;
		}

		Card otherCard = (Card) object;

		if (this.value == null) {

			return otherCard.value == null;
		}
		if (otherCard.value == null) {

			return false;
		}

		return this.value.toLowerCase().equals(otherCard.value.toLowerCase());
	}

	public int hashCode() {

		return 0;
	}
}
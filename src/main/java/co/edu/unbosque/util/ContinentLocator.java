package co.edu.unbosque.util;

public class ContinentLocator {

	public ContinentLocator() {
		// TODO Auto-generated constructor stub
	}

	public static String getContinent(String country) {
		String[] asia = { "Afghanistan", "Armenia", "Azerbaijan", "Bahrain", "Bangladesh", "Bhutan", "Brunei",
				"Cambodia", "China", "Cyprus", "Georgia", "India", "Indonesia", "Iran", "Iraq", "Israel", "Japan",
				"Jordan", "Kazakhstan", "Kuwait", "Kyrgyzstan", "Laos", "Lebanon", "Malaysia", "Maldives", "Mongolia",
				"Myanmar", "Nepal", "North Korea", "Oman", "Pakistan", "Palestine", "Philippines", "Qatar",
				"Saudi Arabia", "Singapore", "South Korea", "Sri Lanka", "Syria", "Taiwan", "Tajikistan", "Thailand",
				"Timor-Leste", "Turkey", "Turkmenistan", "United Arab Emirates", "Uzbekistan", "Vietnam", "Yemen" };
		for (int i = 0; i < asia.length; i++) {
			if (asia[i].equalsIgnoreCase(country))
				return "Asia";
		}
		String[] africa = { "Algeria", "Angola", "Benin", "Botswana", "Burkina Faso", "Burundi", "Cabo Verde",
				"Cameroon", "Central African Republic", "Chad", "Comoros", "Democratic Republic of the Congo",
				"Djibouti", "Egypt", "Equatorial Guinea", "Eritrea", "Eswatini", "Ethiopia", "Gabon", "Gambia", "Ghana",
				"Guinea", "Guinea-Bissau", "Ivory Coast", "Kenya", "Lesotho", "Liberia", "Libya", "Madagascar",
				"Malawi", "Mali", "Mauritania", "Mauritius", "Morocco", "Mozambique", "Namibia", "Niger", "Nigeria",
				"Republic of the Congo", "Rwanda", "Sao Tome and Principe", "Senegal", "Seychelles", "Sierra Leone",
				"Somalia", "South Africa", "South Sudan", "Sudan", "Tanzania", "Togo", "Tunisia", "Uganda", "Zambia",
				"Zimbabwe" };
		for (int i = 0; i < africa.length; i++) {
			if (africa[i].equalsIgnoreCase(country))
				return "Africa";
		}
		String[] america = { "Antigua and Barbuda", "Argentina", "Bahamas", "Barbados", "Belize", "Bolivia", "Brazil",
				"Canada", "Chile", "Colombia", "Costa Rica", "Cuba", "Dominica", "Dominican Republic", "Ecuador",
				"El Salvador", "Grenada", "Guatemala", "Guyana", "Haiti", "Honduras", "Jamaica", "Mexico", "Nicaragua",
				"Panama", "Paraguay", "Peru", "Saint Kitts and Nevis", "Saint Lucia",
				"Saint Vincent and the Grenadines", "Suriname", "Trinidad and Tobago", "United States", "Uruguay",
				"Venezuela", "Aruba" };
		for (int i = 0; i < america.length; i++) {
			if (america[i].equalsIgnoreCase(country))
				return "America";
		}
		String[] europe = { "Albania", "Andorra", "Austria", "Belarus", "Belgium", "Bosnia and Herzegovina", "Bulgaria",
				"Croatia", "Cyprus", "Czech Republic", "Denmark", "Estonia", "Finland", "France", "Germany", "Greece",
				"Hungary", "Iceland", "Ireland", "Italy", "Kosovo", "Latvia", "Liechtenstein", "Lithuania",
				"Luxembourg", "Malta", "Moldova", "Monaco", "Montenegro", "Netherlands", "North Macedonia", "Norway",
				"Poland", "Portugal", "Romania", "Russia", "San Marino", "Serbia", "Slovakia", "Slovenia", "Spain",
				"Sweden", "Switzerland", "Ukraine", "United Kingdom", "Vatican City" };
		for (int i = 0; i < europe.length; i++) {
			if (europe[i].equalsIgnoreCase(country))
				return "Europe";
		}
		return "Oceania";
	}

}
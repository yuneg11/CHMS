import java.util.ArrayList;
import JSON.JSONObject;
import JSON.JSONArray;
import JSON.parser.JSONParser;

class Mainboard extends Product {
	// Variable
	private String chipset;
	private String formFactor;
	private String cpuSocket;
	private ArrayList<StringLongPair> slots;
	private ArrayList<StringLongPair> ports;

	// Constructor
	public Mainboard() {
		this.productType = "Mainboard";
		slots = new ArrayList<StringLongPair>();
		ports = new ArrayList<StringLongPair>();
	}

	public Mainboard(String attributes) {
		this.productType = "Mainboard";
		slots = new ArrayList<StringLongPair>();
		ports = new ArrayList<StringLongPair>();
		setAttribute(attributes);
	}

	// Setter & Getter
	public void insert(String excludeKeys) {
		super.insert(excludeKeys);

		try {
			JSONObject required = (JSONObject) (new JSONParser().parse(excludeKeys));
			JSONArray keyArray = (JSONArray) required.get("ExcludeKey");
			long types;
			String name;
			long num;

			if (!keyArray.contains(Str.chipset))
				this.chipset = UI.inputLine("Chipset");

			if (!keyArray.contains(Str.formFactor))
				this.formFactor = UI.inputLine("Form Factor");

			if (!keyArray.contains(Str.cpuSocket))
				this.cpuSocket = UI.inputLine("CPU Socket");

			if (!keyArray.contains(Str.slot)) {
				types = UI.inputLong("How many types of slots?");
				for (int i = 0; i < types; i++) {
					name = UI.inputLine("Name of slot type "+String.valueOf(i+1));
					num = UI.inputLong("Number of slot type "+String.valueOf(i+1));
					this.slots.add(new StringLongPair(name, num));
				}
			}

			if (!keyArray.contains(Str.port)) {
				types = UI.inputLong("How many types of ports?");
				for (int i = 0; i < types; i++) {
					name = UI.inputLine("Name of port type "+String.valueOf(i+1));
					num = UI.inputLong("Number of port type "+String.valueOf(i+1));
					this.ports.add(new StringLongPair(name, num));
				}
			}
		} catch (Exception exc) {
			System.out.println("Unexpected error occurred");
		}

	}

	public void print(String excludeKeys) {
		super.print(excludeKeys);

		try {
			JSONObject required = (JSONObject) (new JSONParser().parse(excludeKeys));
			JSONArray keyArray = (JSONArray) required.get("ExcludeKey");
			long types;

			if (!keyArray.contains(Str.chipset))
				System.out.println(UI.content("Chipset: "+this.chipset));

			if (!keyArray.contains(Str.formFactor))
				System.out.println(UI.content("Form Factor: "+this.formFactor));

			if (!keyArray.contains(Str.cpuSocket))
				System.out.println(UI.content("CPU Socket: "+this.cpuSocket));

			if (!keyArray.contains(Str.slot)) {
				types = slots.size();
				for (int i = 0; i < types; i++) {
					System.out.println(UI.subtitle("Slot #"+String.valueOf(i+1)));
					System.out.println(UI.content(this.slots.get(i).name+" - "+String.valueOf(this.slots.get(i).num)+"ea"));
				}
			}

			if (!keyArray.contains(Str.port)) {
				types = ports.size();
				for (int i = 0; i < types; i++) {
					System.out.println(UI.subtitle("Port #"+String.valueOf(i+1)));
					System.out.println(UI.content(this.ports.get(i).name+" - "+String.valueOf(this.ports.get(i).num)+"ea"));
				}
			}
		} catch (Exception exc) {
			System.out.println("Unexpected error occurred");
		}

	}

	public void setAttribute(String attributes) {
		/* attributes : {"Name":"i5-750", "Price":210000, "Manufacturer":"Intel"} */
		try {
			JSONObject obj = (JSONObject) (new JSONParser().parse(attributes));
			if (obj.containsKey(Str.productType))
				this.productType = (String) obj.get(Str.productType);
			if (obj.containsKey("Name"))
				this.name = (String) obj.get("Name");
			if (obj.containsKey("Price"))
				this.price = (Long) obj.get("Price");
			if (obj.containsKey("Manufacturer"))
				this.manufacturer = (String) obj.get("Manufacturer");
			if (obj.containsKey("Chipset"))
				this.chipset = (String) obj.get("Chipset");
			if (obj.containsKey("FormFactor"))
				this.formFactor = (String) obj.get("FormFactor");
			if (obj.containsKey("CPUSocket"))
				this.cpuSocket = (String) obj.get("CPUSocket");
			if (obj.containsKey(Str.slot)) {
				JSONArray values = (JSONArray) obj.get(Str.slot);
				for (Object value : values) {
					slots.add(new StringLongPair((String) ((JSONObject) value).get("Name"),
							(Long) ((JSONObject) value).get("Number")));
				}
			}
			if (obj.containsKey(Str.port)) {
				JSONArray values = (JSONArray) obj.get(Str.port);
				for (Object value : values) {
					ports.add(new StringLongPair((String) ((JSONObject) value).get("Name"),
							(Long) ((JSONObject) value).get("Number")));
				}
			}
		} catch (Exception exc) {
			System.out.println("Unexpected error occurred");
		}
	}

	public String getAttribute(String keys) {
		/* keys : {"Keys":["ProductType", "Name", "Price"]} */
		try {
			JSONObject required = (JSONObject) (new JSONParser().parse(keys));
			JSONArray keyArray = (JSONArray) required.get("Keys");
			JSONObject obj = new JSONObject();
			for (Object key : keyArray) {
				switch ((String) key) {
				case "ProductType":
					obj.put(Str.productType, "Mainboard");
					break;
				case "Name":
					if (this.name != null)
						obj.put("Name", this.name);
					break;
				case "Price":
					if (this.price != null)
						obj.put("Price", this.price);
					break;
				case "Manufacturer":
					if (this.manufacturer != null)
						obj.put("Manufacturer", this.manufacturer);
					break;
				case "Chipset":
					if (this.chipset != null)
						obj.put("Chipset", this.chipset);
					break;
				case "FormFactor":
					if (this.formFactor != null)
						obj.put("FormFactor", this.formFactor);
					break;
				case "CPUSocket":
					if (this.cpuSocket != null)
						obj.put("CPUSocket", this.cpuSocket);
					break;
				case "Slot":
					if (slots.size() != 0) {
						JSONArray values = new JSONArray();
						for (StringLongPair value : slots) {
							JSONObject slp = new JSONObject();
							slp.put("Name", value.name);
							slp.put("Number", value.num);
							values.add(slp);
						}
						obj.put("Slot", values);
					}
					break;
				case "Port":
					if (ports.size() != 0) {
						JSONArray values = new JSONArray();
						for (StringLongPair value : ports) {
							JSONObject slp = new JSONObject();
							slp.put("Name", value.name);
							slp.put("Number", value.num);
							values.add(slp);
						}
						obj.put("Port", values);
					}
					break;
				}
			}
			return obj.toJSONString();
		} catch (Exception exc) {
			System.out.println("Unexpected error occurred");
			return null;
		}
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		JSONArray keyArray = new JSONArray();
		keyArray.add(Str.productType);
		keyArray.add(Str.name);
		keyArray.add(Str.price);
		keyArray.add(Str.manufacturer);
		keyArray.add(Str.chipset);
		keyArray.add(Str.formFactor);
		keyArray.add(Str.cpuSocket);
		keyArray.add(Str.slot);
		keyArray.add(Str.port);
		obj.put("Keys", keyArray);
		try {
			return (JSONObject) (new JSONParser().parse(getAttribute(obj.toJSONString())));
		} catch (Exception ex) {
			System.out.println("Unexpected error occurred");
			return null;
		}
	}
}
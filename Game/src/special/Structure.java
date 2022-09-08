package special;

public class Structure {

	public int width, height;
	public String[][] data = null;

	public Structure(String name) {
				
		if(name == "house_left") {
			
			width = 5;
			height = 5;
			data = new String[][] {
				{"house_left_1", "house_left_2", "house_left_2", "house_left_2", "house_left_3"},
				{"house_left_4", "house_left_5", "house_left_5", "house_left_5", "house_left_6"},
				{"house_left_7", "house_left_8", "house_left_8", "house_left_8", "house_left_9"},
				{"house_left_10", "house_left_11", "house_left_12", "house_left_13", "house_left_14"},
				{"house_left_15", "house_left_16", "house_left_17", "house_left_18", "house_left_19"}
			};
			
		}
		
		if(name == "house_right") {
			
			width = 5;
			height = 5;
			data = new String[][] {
				{"house_right_1", "house_right_2", "house_right_2", "house_right_2", "house_right_3"},
				{"house_right_4", "house_right_5", "house_right_5", "house_right_5", "house_right_6"},
				{"house_right_7", "house_right_8", "house_right_8", "house_right_8", "house_right_9"},
				{"house_right_10", "house_right_11", "house_right_12", "house_right_13", "house_right_14"},
				{"house_right_15", "house_right_16", "house_right_17", "house_right_18", "house_right_19"}
			};
			
		}
		
		if(name == "prof_house") {
			
			width = 7;
			height = 5;
			data = new String[][] {
				{"prof_house_1", "prof_house_2", "prof_house_3", "prof_house_4", "prof_house_4", "prof_house_4", "prof_house_5"},
				{"prof_house_6", "prof_house_7", "prof_house_8", "prof_house_9", "prof_house_9", "prof_house_9", "prof_house_10"},
				{"prof_house_6", "prof_house_9", "prof_house_9", "prof_house_9", "prof_house_9", "prof_house_9", "prof_house_10"},
				{"prof_house_11", "prof_house_12", "prof_house_12", "prof_house_13", "prof_house_14", "prof_house_15", "prof_house_16"},
				{"prof_house_17", "prof_house_18", "prof_house_18", "prof_house_19", "prof_house_20", "prof_house_21", "prof_house_22"}
			};
			
		}
		
		if(name == "tree") {
			
			width = 2;
			height = 3;
			data = new String[][] {
				{"tree_1", "tree_2"},
				{"tree_3", "tree_4"},
				{"tree_5", "tree_6"}
			};
			
		}
		
	}

}

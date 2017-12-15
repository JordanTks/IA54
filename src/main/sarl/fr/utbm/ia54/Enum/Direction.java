package fr.utbm.ia54.Enum;

public enum Direction {
	
	NORTH {
		
		@Override
		public Direction opposite() {
			return SOUTH;
		}
		
	},
	
	SOUTH {
		
		@Override
		public Direction opposite() {
			return NORTH;
		}
		
	},
	
	EAST {
		
		@Override
		public Direction opposite() {
			return WEST;
		}
		
	},
	
	WEST {
		
		@Override
		public Direction opposite() {
			return EAST;
		}
		
	},
	
	UNKNOWN {
		
		@Override
		public Direction opposite() {
			return UNKNOWN;
		}
		
	};
	
	public abstract Direction opposite();
}

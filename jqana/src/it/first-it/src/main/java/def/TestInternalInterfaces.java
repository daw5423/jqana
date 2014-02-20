package def;

public class TestInternalInterfaces {
	interface xpto {
		void teste(int x);
	}
	
	xpto a;
	
	public void method1() {
		class teste1 implements xpto {

			@Override
			public void teste(int x) {
				
				
			}
			
		}
		
		this.a = new teste1();
	}
}

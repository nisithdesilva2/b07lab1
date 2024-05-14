public class Polynomial{
	private double[] coef;

	public Polynomial(){
		this.coef = new double[0];
	}

	public Polynomial(double[] coef){
		this.coef = coef;
	}

	public Polynomial add(Polynomial p){
		double[] pCoef = p.getCoef();
		double[] newCoef;
		int length;
		int length2;
		
		if(pCoef.length > this.coef.length){
			newCoef = new double[pCoef.length];
			for(int i = 0; i < this.coef.length; i++){
				newCoef[i] = pCoef[i] + this.coef[i];
			}
			for(int j = this.coef.length; j < pCoef.length; j++){
				newCoef[j] = pCoef[j];
			}
		}
		else{
			newCoef = new double[this.coef.length];
			for(int i = 0; i < pCoef.length; i++){
				newCoef[i] = pCoef[i] + this.coef[i];
			}
			for(int j = pCoef.length; j < this.coef.length; j++){
				newCoef[j] = this.coef[j];
			}
		}

		Polynomial newP = new Polynomial(newCoef);
		return newP;
	}

	public double evaluate(double x){
		double val = 0;

		for(int i = 0; i < this.coef.length; i++){
			val += Math.pow(x, i) * this.coef[i];
		}

		return val;
	}

	public boolean hasRoot(double x){
		double val = 0;
		val = this.evaluate(x);
		
		if(val == 0){
			return true;
		}
		return false;
	}

	public double[] getCoef(){
		return this.coef;
	}
}
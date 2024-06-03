import java.io.*;

public class Polynomial{
	private double[] coef;
	private int[] exps;

	public Polynomial(){
		this.coef = new double[1];
		this.exps = new int[1];
	}

	public Polynomial(File file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
	    String line = br.readLine();
	    
	    String formatted = line.replace("-", "+-");
	    
	    String[] firstSplit = formatted.replaceFirst("^\\+", "").split("\\+");
	    
	    this.coef = new double[firstSplit.length];
	    this.exps = new int[firstSplit.length];
	    
	    for(int i = 0; i < firstSplit.length; i++){
	        if(!firstSplit[i].contains("x")){
	            this.coef[i] = Double.parseDouble(firstSplit[i]);
	           	this.exps[i] = 0;
	            continue;
	        }
	        
	        String[] split2 = firstSplit[i].split("x", -1);
	        
	        if(split2[0].equals("")){
	            this.coef[i] = 1;
	        }
	        else if(split2[0].equals("-")){
	            this.coef[i] = -1;
	        }
	        else{
	            this.coef[i] = Double.parseDouble(split2[0]);
	        }
	        
	        if(split2[1].equals("")){
	            this.exps[i] = 1;
	        }
	        else{
	            this.exps[i] = Integer.parseInt(split2[1]);
	        }
	    }

		br.close();
	}

	public Polynomial(double[] coef, int[] exps){
		this.coef = coef;
		this.exps = exps;
	}

	public Polynomial add(Polynomial p){
		if(p == null || p.getCoef().length != p.getExps().length){
	        return null;
	    }
	    
	    double[] pCoef = p.getCoef();
	    int[] pExps = p.getExps();
	    
	    int[] newExps = new int[exps.length + pExps.length];
	    double[] newCoefs = new double[exps.length + pExps.length];
	    int i = 0, j = 0, k = 0;
	    
	    while(i < exps.length && j < pExps.length){
	        if(exps[i] < pExps[j]){
	            newExps[k] = exps[i];
	            newCoefs[k++] = coef[i++];
	        }
	        else if(exps[i] == pExps[j]){
	            newExps[k] = exps[i];
	            newCoefs[k++] = coef[i++] + pCoef[j++];
	        }
	        else{
	            newExps[k] = pExps[j];
	            newCoefs[k++] = pCoef[j++];
	        }
	    }
	    
	    while(i < exps.length){
	        newExps[k] = exps[i];
	        newCoefs[k++] = coef[i++];
	    }
	    
	    while(j < pExps.length){
	        newExps[k] = pExps[j];
	        newCoefs[k++] = pCoef[j++];
	    }
	    
	    double[] finalCoefs = new double[k];
	    int[] finalExps = new int[k];
	    
	    for(int n = 0; n < k; n++){
	        finalCoefs[n] = newCoefs[n];
	        finalExps[n] = newExps[n];
	    }
	    
	    Polynomial pSum = new Polynomial(finalCoefs, finalExps);
	    return pSum;
	}

	public double evaluate(double x){
		double val = 0;

		for(int i = 0; i < this.coef.length; i++){
			val += Math.pow(x, exps[i]) * this.coef[i];
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
	
	public int[] getExps(){
		return this.exps;
	}
	
	public Polynomial multiply(Polynomial p){
		Polynomial mult = new Polynomial();
		
		for(int i = 0; i < coef.length; i++){
	        	double[] multCoefs = new double[p.getCoef().length];
	        	int[] multExps = new int[p.getExps().length];
	        
	        	for(int j = 0; j < p.getCoef().length; j++){
	            		multCoefs[j] = coef[i]*p.getCoef()[j];
	            		multExps[j] = exps[i] + p.getExps()[j];
	        	}
		
			Polynomial toAdd = new Polynomial(multCoefs, multExps);

			mult = mult.add(toAdd);
		}
		
		return mult;
	}

	public void saveToFile(String file) throws IOException{
		FileWriter writer = new FileWriter(file);

		String p = "";

		for(int i = 0; i < this.coef.length; i++){
			if(this.coef[i] == 1){
				p += "";
			}
			else if(this.coef[i] == -1){
				p += "-";
			}
			else{
				p += this.coef[i];
			}

			p += "x";
			p += this.exps[i];

			if(i != this.coef.length - 1){
				p += "+";
			}
		}

		writer.write(p);
		writer.close();
	}
}
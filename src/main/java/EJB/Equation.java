package EJB;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Equation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	int number1;
	int number2;
	String operation;
	
	public Equation(){}
	
	public Equation(int num1, int num2, String ch) {
		this.number1 = num1;
		this.number2 = num2;
		this.operation = ch;
	}
	
	public int getNumber1() {
		return number1;
	}
	
	public void setNumber1(int number1) {
		this.number1 = number1;
	}
	
	public int getNumber2() {
		return number2;
	}
	
	public void setNumber2(int number2) {
		this.number2 = number2;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
}

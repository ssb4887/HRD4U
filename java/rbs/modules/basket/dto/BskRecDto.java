package rbs.modules.basket.dto;

public class BskRecDto {
	
	private String title;
	
	private String salaryStle;
	
	private String salary;
	
	private String salaryScope;
	
	
	private String region;
	
	private String workStle;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSalaryStle() {
		return salaryStle;
	}

	public void setSalaryStle(String salaryStle) {
		this.salaryStle = salaryStle;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getSalaryScope() {
		return salaryScope;
	}

	public void setSalaryScope(String salaryScope) {
		this.salaryScope = salaryScope;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getWorkStle() {
		return workStle;
	}

	public void setWorkStle(String workStle) {
		this.workStle = workStle;
	}

	public BskRecDto(String title, String salaryStle, String salary,
			String salaryScope, String region, String workStle) {
		super();
		this.title = title;
		this.salaryStle = salaryStle;
		this.salary = salary;
		this.salaryScope = salaryScope;
		this.region = region;
		this.workStle = workStle;
	}

	public BskRecDto() {
		super();
	}

	@Override
	public String toString() {
		return "BskRecDto [title=" + title + ", salaryStle=" + salaryStle
				+ ", salary=" + salary + ", salaryScope=" + salaryScope
				+ ", region=" + region + ", workStle=" + workStle + "]";
	}

	
	
	
	
	
}

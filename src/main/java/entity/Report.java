package entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SergeyK
 */
public class Report {
	
	public Report() {
	}

	private Timestamp printtime;
	private int countCheck;
	private int countCancelCheck;
	List<Detail> detail = new ArrayList<>();

	/**
	 * @param timestamp
	 */
	public void setPrinttime(Timestamp printtime) {
		this.printtime = printtime;		
	}
	
	/**
	 * @return the printtime
	 */
	public Timestamp getPrinttime() {
		return printtime;
	}

	/**
	 * @param countCheck
	 */
	public void setCountCheck(int countCheck) {
		this.countCheck = countCheck;
	}
	
	/**
	 * @return the countCheck
	 */
	public int getCountCheck() {
		return countCheck;
	}
	
	/**
	 * @param countCheck
	 */
	public void setCountCancelCheck(int countCancelCheck) {
		this.countCancelCheck = countCancelCheck;
	}

	/**
	 * @return the countCancelCheck
	 */
	public int getCountCancelCheck() {
		return countCancelCheck;
	}

	/**
	 * @return the detail
	 */
	public List<Detail> getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(List<Detail> detail) {
		this.detail = detail;
	}
	
	public class Detail {

		public Detail() {
			super();
		}		
		/**
		 * @param nds
		 * @param ndsTotal
		 * @param total
		 */
		public Detail(int nds, double ndsTotal, double total) {
			this.nds = nds;
			this.ndsTotal = ndsTotal;
			this.total = total;
		}

		public int getNds() {
			return nds;
		}

		public void setNds(int nds) {
			this.nds = nds;
		}

		public double getNdsTotal() {
			return ndsTotal;
		}

		public void setNdsTotal(double ndsTotal) {
			this.ndsTotal = ndsTotal;
		}

		public double getTotal() {
			return total;
		}
		public void setTotal(double total) {
			this.total = total;
		}
		private int nds;
		private double ndsTotal;
		private double total;
	}
}

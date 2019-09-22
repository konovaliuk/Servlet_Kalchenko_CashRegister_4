package service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для формирования Х-отчета, Z-отчета
 * @author SergeyK
 */
public class Report {

	private long number;
	private Timestamp printtime;
	private int countCheck;
	private int countCancelCheck;
	private double totalA;
	private double ndsTotalA;
	private double totalB;
	private double ndsTotalB;
	private double totalC;
	private double ndsTotalC;
	private double sumNdsTotal;
	private double sumTotal;
	private List<Detail> detail = new ArrayList<>();
	
	public Report() {
	}

	// <editor-fold defaultstate="collapsed" desc=" getters and setters">
	/**
	 * Установить номер отчета
	 * @param number номер отчета
	 */
	public void setNumber(long number) {
		this.number = number;
	}
	/**
	 * Получить номер отчета
	 * @return number номер отчета
	 */
	public long getNumber() {
		return number;
	}
	/**
	 * Установить время печати
	 * @param timestamp время печати
	 */
	public void setPrinttime(Timestamp printtime) {
		this.printtime = printtime;		
	}
	
	/**
	 * Получить время печати
	 * @return printtime время печати
	 */
	public Timestamp getPrinttime() {
		return printtime;
	}

	/**
	 * Установить количество чеков
	 * @param countCheck количество чеков
	 */
	public void setCountCheck(int countCheck) {
		this.countCheck = countCheck;
	}
	
	/**
	 * Получить количество чеков
	 * @return countCheck количество чеков
	 */
	public int getCountCheck() {
		return countCheck;
	}
	
	/**
	 * Установить количество отмененных чеков
	 * @param countCheck количество отмененных чеков
	 */
	public void setCountCancelCheck(int countCancelCheck) {
		this.countCancelCheck = countCancelCheck;
	}

	/**
	 * Получить количество отмененных чеков
	 * @return countCheck количество отмененных чеков
	 */
	public int getCountCancelCheck() {
		return countCancelCheck;
	}

	/**
	 * Получить список записей блока detail 
	 * @param detail список записей 
	 */
	public List<Detail> getDetail() {
		return detail;
	}

	/**
	 * Установить список записей для блока detail 
	 * @param detail список записей 
	 */
	public void setDetail(List<Detail> detail) {
		this.detail = detail;
	}
	
	public double getSumNdsTotal() {
		return sumNdsTotal;
	}

	public void setSumNdsTotal(double sumNdsTotal) {
		this.sumNdsTotal = sumNdsTotal;
	}

	public double getSumTotal() {
		return sumTotal;
	}
	
	public void setSumTotal(double sumTotal) {
		this.sumTotal = sumTotal;
	}
	
	/**
	 * @return the totalA
	 */
	public double getTotalA() {
		return totalA;
	}

	/**
	 * @param totalA the totalA to set
	 */
	public void setTotalA(double totalA) {
		this.totalA = totalA;
	}

	/**
	 * @return the ndsTotalA
	 */
	public double getNdsTotalA() {
		return ndsTotalA;
	}

	/**
	 * @param ndsTotalA the ndsTotalA to set
	 */
	public void setNdsTotalA(double ndsTotalA) {
		this.ndsTotalA = ndsTotalA;
	}

	/**
	 * @return the totalB
	 */
	public double getTotalB() {
		return totalB;
	}

	/**
	 * @param totalB the totalB to set
	 */
	public void setTotalB(double totalB) {
		this.totalB = totalB;
	}

	/**
	 * @return the ndsTotalB
	 */
	public double getNdsTotalB() {
		return ndsTotalB;
	}

	/**
	 * @param ndsTotalB the ndsTotalB to set
	 */
	public void setNdsTotalB(double ndsTotalB) {
		this.ndsTotalB = ndsTotalB;
	}
	
	/**
	 * @param ndsTotalC the ndsTotalC to set
	 */
	public void setNdsTotalC(double ndsTotalC) {
		this.ndsTotalC = ndsTotalC;
	}	

	/**
	 * @return the totalC
	 */
	public double getTotalC() {
		return totalC;
	}

	/**
	 * @param totalB the totalB to set
	 */
	public void setTotalC(double totalC) {
		this.totalC = totalC;
	}

	/**
	 * @return the ndsTotalC
	 */
	public double getNdsTotalC() {
		return ndsTotalC;
	}
	// </editor-fold>

	/**
	 * Класс для вывода на печать в очет детальных позиций
	 */
	public class Detail {

		private int nds;
		private double ndsTotal;
		private double total;
		
		public Detail() {
			super();
		}		
		/**
		 * @param nds ставка НДС
		 * @param ndsTotal Сумма НДС (по каждой ставке НДС)
		 * @param total Сумма (по каждой ставке НДС)
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
	}

    public class Builder {
        private Report newReport;
 
        public Builder() {
        	newReport = new Report();
        }
 
        public Report build(){
            return newReport;
        }
		public Builder addNumber(long number) {
			newReport.setNumber(number);
			return this;
		}	
		public Builder addPrinttime(Timestamp printtime) {
			newReport.printtime = printtime;
			return this;
		}		
		public Builder addCountCheck(int countCheck) {
			newReport.countCheck = countCheck;
			return this;
		}
		public Builder addCountCancelCheck(int countCancelCheck) {
			newReport.countCancelCheck = countCancelCheck;
			return this;
		}
		public Builder addSumTotal(double sumTotal) {
			newReport.sumTotal = sumTotal;
			return this;
		}
		public Builder addSumNdsTotal(double sumNdsTotal) {
			newReport.sumNdsTotal = sumNdsTotal;
			return this;
		}
		public Builder addTotalA(double totalA) {
			newReport.totalA = totalA;
			return this;
		}
		public Builder addTotalB(double totalB) {
			newReport.totalB = totalB;
			return this;
		}
		public Builder addTotalC(double totalC) {
			newReport.totalC = totalC;
			return this;
		}
		public Builder addNdsTotalA(double ndsTotalA) {
			newReport.ndsTotalA = ndsTotalA;
			return this;
		}
		public Builder addNdsTotalB(double ndsTotalB) {
			newReport.ndsTotalB = ndsTotalB;
			return this;
		}
		public Builder addNdsTotalC(double ndsTotalC) {
			newReport.ndsTotalC = ndsTotalC;
			return this;
		}
    }
}

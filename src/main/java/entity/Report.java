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
	
	/**
	 * Класс для вывода на печать в очет детальных позиций
	 */
	public class Detail {

		public Detail() {
			super();
		}		
		/**
		 * @param nds ставка НДС
		 * @param ndsTotal Сумма НДС
		 * @param total Сумма
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

/**
 * @author Ahmet Gedemenli
 *
 * Created for CMPE 321 - Project 2
 * 2014400147
 */

public class PageHeader {

	/**
	 * Properties of a page header as described in the report.
	 */
	private Integer pageId;

	private Integer numberOfRecords;

	private Boolean isFull;

	private Boolean isEmpty;

	/**
	 * Constructor.
	 */
	public PageHeader(Integer pageId) {
		this.pageId = pageId;
		this.numberOfRecords = 0;
		this.isFull = false;
		this.isEmpty = true;
	}

	/**
	 * Getters and setters.
	 */
	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public Boolean getFull() {
		return isFull;
	}

	public void setFull(Boolean full) {
		isFull = full;
	}

	public Boolean getEmpty() {
		return isEmpty;
	}

	public void setEmpty(Boolean empty) {
		isEmpty = empty;
	}

	/**
	 * Convert into string.
	 */
	@Override
	public String toString() {
		return pageId + " " + numberOfRecords + " " + isFull + " " + isEmpty + " ";
	}
}

/**
 * @author Ahmet Gedemenli
 *
 * Created for CMPE 321 - Project 2
 * 2014400147
 */

import java.util.ArrayList;

public class Page {

	/**
	 * Properties of a page as described in the report.
	 */
	private PageHeader pageHeader;

	private ArrayList<Record> records;

	/**
	 * Constructor.
	 */
	public Page(PageHeader pageHeader) {
		this.pageHeader = pageHeader;
		records = new ArrayList<>();
	}

	/**
	 * Getters and setters.
	 */
	public PageHeader getPageHeader() {
		return pageHeader;
	}

	public void setPageHeader(PageHeader pageHeader) {
		this.pageHeader = pageHeader;
	}

	public ArrayList<Record> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<Record> records) {
		this.records = records;
	}

	/**
	 * Convert into string.
	 */
	@Override
	public String toString() {
		String recordsString = "";
		for (Record record : this.records) {
			recordsString = recordsString + record.toString();
		}
		return pageHeader.toString() + recordsString;
	}
}

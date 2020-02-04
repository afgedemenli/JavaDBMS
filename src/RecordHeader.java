/**
 * @author Ahmet Gedemenli
 *
 * Created for CMPE 321 - Project 2
 * 2014400147
 */

public class RecordHeader {

	/**
	 * Properties of a record header as described in the report.
	 */
	private RecordType recordType;

	/**
	 * Constructor.
	 */
	public RecordHeader(RecordType recordType) {
		this.recordType = recordType;
	}

	/**
	 * Getters and setters.
	 */
	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	/**
	 * Convert into string.
	 */
	@Override
	public String toString() {
		return recordType.toString();
	}
}

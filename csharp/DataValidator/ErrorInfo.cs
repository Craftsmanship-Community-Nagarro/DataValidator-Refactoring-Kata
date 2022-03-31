namespace DataValidator;

public class ErrorInfo : Object
{
	private int Row;
	private string DetailMessage;

	public ErrorInfo(int row, string detailMessage)
	{
		this.Row = row;
		this.DetailMessage = detailMessage;
	}

	public string Message()
	{
		return string.Format("Invalid data in row {0} {1}", Row, DetailMessage);
	}
}

package project.main.dto;

public class BoardDTO {
	private String boardNo;
    private String boardTitle;
    private String boardDate;
    private String boardContents;
 
    public String getBoardNo() {
        return boardNo;
    }
 
    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }
 
    public String getBoardTitle() {
        return boardTitle;
    }
 
    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }
 
    public String getBoardDate() {
        return boardDate;
    }
 
    public void setBoardDate(String boardDate) {
        this.boardDate = boardDate;
    }

    public String getBoardContents() {
        return boardContents;
    }
 
    public void setBoardContents(String boardContents) {
        this.boardContents = boardContents;
    }

	@Override
	public String toString() {
		
		return "boardNo:"+boardNo+"/boardTitle:"+boardTitle+"/boardDate:"+boardDate+"/boardContents:"+boardContents;
	}
    
}

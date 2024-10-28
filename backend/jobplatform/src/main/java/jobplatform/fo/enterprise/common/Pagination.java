package jobplatform.fo.enterprise.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
	private int totalDataCount; // 총 데이터 개수* 
	private int showDataCountPerPage;  // 1페이지당 데이터 수*  
	private int totalPageCount;  // 총 페이지 개수              
	private int startRowIndex;   // 보여주기 시작할 글의 row index 번호   
	private int pageNo;          // 선택한 페이지 번호*                   
	
	private int totalPageGroupsCount;    // 총 페이지 그룹 개수            
	private int showPageGroupsCount;     // 한번에 보여질 페이지 개수 *                 
	private int pageGroupsOfCurrentPage; // 현재 페이지가 속한 페이징 그룹 번호             
	private int startNumOfPageGroups;    // 페이지 그룹의 첫페이지 번호
	private int endNumOfPageGroups;      // 페이지 그룹의 마지막페이지 번호
	
	public Pagination(int totalDataCount, int showDataCountPerPage, int pageNo, int showPageGroupsCount) {
        this.totalDataCount = totalDataCount;
        this.showDataCountPerPage = showDataCountPerPage;
        this.totalPageCount = (int) Math.ceil((double) totalDataCount / showDataCountPerPage);
        this.startRowIndex = (pageNo - 1) * showDataCountPerPage;
        this.pageNo = pageNo;

        this.showPageGroupsCount = Math.min(showPageGroupsCount, totalPageCount);
        this.totalPageGroupsCount = (int) Math.ceil((double) totalPageCount / this.showPageGroupsCount);
        this.pageGroupsOfCurrentPage = (int) Math.ceil((double) pageNo / this.showPageGroupsCount);
        this.startNumOfPageGroups = (int) Math.ceil(((double) (pageGroupsOfCurrentPage - 1) * this.showPageGroupsCount) + 1);

        this.endNumOfPageGroups = Math.min(pageGroupsOfCurrentPage * this.showPageGroupsCount, totalPageCount);
    }

    public Pagination(int totalDataCount, int pageNo) {
        this(totalDataCount, 10, pageNo, 5);
    }
}

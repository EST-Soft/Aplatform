class Pagination {
    constructor(totalListCnt, page, pageSize, blockSize) {
        this.pageSize = pageSize; // 페이지당 보여지는 게시글 최대 개수
        this.page = page; // 현재 페이지
        this.totalListCnt = totalListCnt; // 총 게시글 수
        
        // 총 페이지 수
        this.totalPageCnt = Math.ceil(totalListCnt / this.pageSize);
        
        // 총 블럭 수
        this.totalBlockCnt = Math.ceil(this.totalPageCnt / blockSize);
        
        // 현재 블럭
        this.block = Math.ceil(page / blockSize);
        
        // 블럭 시작 페이지
        this.startPage = ((this.block - 1) * blockSize) + 1;
        
        // 블럭 마지막 페이지
        this.endPage = this.startPage + blockSize - 1;

        // 블럭 마지막 페이지 validation
        if (this.endPage > this.totalPageCnt) {
            this.endPage = this.totalPageCnt;
        }

        // 이전 블럭
        this.prevBlock = (this.block * blockSize) - blockSize;

        // 이전 블럭 validation
        if (this.prevBlock < 1) {
            this.prevBlock = 1;
        }

        // 다음 블럭
        this.nextBlock = (this.block * blockSize) + 1;

        // 다음 블럭 validation
        if (this.nextBlock > this.totalPageCnt) {
            this.nextBlock = this.totalPageCnt;
        }

        // 인덱스
        this.startIndex = (this.page - 1) * this.pageSize;
    }
}

module.exports = Pagination;
<template>
    <div class="board-list-container">
        <header class="header">
            <h1>인재리스트</h1>
        </header>

        <div class="board-content">
        <div class="search-box">

        </div>
        <div class="table-responsive">
            <table class="table table-hover table-striped">
            <thead>
                <tr>
                <th>이름</th>
                <th>이력서 목록</th>
                <th>제안</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="board in boardList" :key="board.brdSq">
                <td class="small-text">{{ board.createdBy }}</td>
                <td class="small-text">{{ board.insrtDtm.slice(0, 10) }}</td>
                <td class="small-text">{{ board.brdHits }}</td>
                </tr>
            </tbody>
            </table>
        </div>
        <div class="pagination-wrapper">
            <ul class="pagination justify-content-center">
            <li class="page-item" :class="{ disabled: curPage === 1 }">
                <button
                class="page-link"
                @click="goToPage(1)"
                :disabled="curPage === 1"
                >
                &lt;&lt;
                </button>
            </li>
            <li class="page-item" :class="{ disabled: curPage === 1 }">
                <button
                class="page-link"
                @click="goToPage(curPage - 1)"
                :disabled="curPage === 1"
                >
                &lt;
                </button>
            </li>
            <li
                v-for="(page, index) in pageList"
                :key="index"
                class="page-item"
                :class="{ active: curPage === page }"
            >
                <button class="page-link" @click="goToPage(page)">
                {{ page }}
                </button>
            </li>
            <li class="page-item" :class="{ disabled: curPage === lastPage }">
                <button
                class="page-link"
                @click="goToPage(curPage + 1)"
                :disabled="curPage === lastPage"
                >
                &gt;
                </button>
            </li>
            <li class="page-item" :class="{ disabled: curPage === lastPage }">
                <button
                class="page-link"
                @click="goToPage(lastPage)"
                :disabled="curPage === lastPage"
                >
                &gt;&gt;
                </button>
            </li>
            </ul>
        </div>
        </div>
    </div>
</template>

<style scoped>
.board-list-container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  text-align: center;
  margin-bottom: 20px;
}

.board-content {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  text-align: center;
}

.table {
  width: 100%;
  margin-bottom: 20px;
}

.table th,
.table td {
  vertical-align: middle;
}

.title-cell {
  width: 70%;
}

.title-cell:hover {
  cursor: pointer;
}

.title-cell a {
  color: #007bff;
  text-decoration: none;
}

.title-cell a:hover {
  text-decoration: underline;
}

.small-text {
  font-size: 0.875rem;
}

.pagination-wrapper {
  margin-top: 20px;
}

.page-link {
  color: #007bff;
  text-decoration: none;
}

.page-item.active .page-link {
  background-color: #007bff;
  border-color: #007bff;
  color: #fff;
}

.page-item.disabled .page-link {
  pointer-events: none;
  color: #6c757d;
}

.search-area {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding: 20px;
  display: flex;
  justify-content: center;
}

.search-select,
.search-input {
  margin-right: 10px;
  padding: 5px;
}

.btn-primary {
  padding: 5px 10px;
}

.justify-content-end {
  align-items: center;
}
.search-box {
  display: flex;
  justify-content: space-between;
}
</style>
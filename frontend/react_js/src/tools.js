// src/tools.js
function formatDateYMD(dateString) {
    if (!dateString) {
        return ''; // 빈 문자열을 반환하거나 기본값을 설정
    }

    const date = new Date(dateString);
    if (isNaN(date)) {
        console.error(`Invalid date: ${dateString}`);
        return ''; // 빈 문자열을 반환하거나 기본값을 설정
    }

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

export { formatDateYMD };

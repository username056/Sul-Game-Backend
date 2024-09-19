(function() {
  // 현재 페이지의 Content-Type이 'application/json'인지 확인
  const contentType = document.contentType || document.mimeType;

  if (contentType && contentType.includes('application/json')) {
    try {
      // 페이지의 전체 텍스트를 가져옴
      const bodyText = document.body.textContent.trim();
      // JSON 파싱
      const data = JSON.parse(bodyText);

      // 토큰 추출
      const accessToken = data.accessToken;
      const refreshToken = data.refreshToken;

      if (accessToken && refreshToken) {
        // 토큰을 localStorage에 저장
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken);

        alert("로그인 성공: 토큰 저장 완료!");
        // 홈 페이지로 리디렉션
        window.location.href = '/';
      } else {
        alert("토큰이 없습니다. 로그인 실패.");
      }
    } catch (e) {
      console.error('JSON 파싱 오류:', e);
    }
  }
})();

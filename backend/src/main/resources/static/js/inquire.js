$(document).ready(function () {
  $(".right").on("click", function (e) {
    let div = $(this).siblings(".admin-answer");

    if (div.css("display") == "block") {
      div.fadeOut(500);
    } else {
      div.fadeIn(500);
    }
  });

  $(".btn").on("click", function () {
    const text = $(".input-text").val().trim();

    if (text == null || text == "") {
      showAlert("문의 & 피드백할 내용을 입력해주세요.");
      return;
    }

    if ($.cookie("register-inquire") == "done") {
      showAlert("5분 후에 다시 작성하실 수 있습니다.");
      return;
    }
    turnLoading();
    fetch("/inquire/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ inquiries: text }),
    })
      .then((res) => {
        if (!res.ok) {
          if (res.status === 429) {
            alert("1분 후 다시 시도해주세요.");
          } else if (res.status === 400) {
            alert("입력값을 확인해주세요.");
          } else {
            alert("서버 오류가 발생했습니다.");
          }
          throw new Error("POST 실패");
        }
        return res.json();
      })
      .then((resData) => {
        var date = new Date();
        date.setTime(date.getTime() + 5 * 60 * 1000); // 1분
        $.cookie("register-inquire", "done", { expires: date, path: "/" });

        let li = $("#exam").clone(true);
        li.children(".text").text(resData.content);
        li.children(".admin-answer").children(".answer").text(resData.answer);
        li.css("display", "");

        $(".inquireList").append(li);
        turnLoading();
      })
      .catch((err) => {
        console.error(err);
        window.location.href = `/toinquire`;
      });
  });

  function showAlert(msg) {
    $.confirm({
      theme: "supervan",
      title: "",
      content: msg,
      buttons: {
        네: function () {},
      },
    });
  }
});



// // Отображение модального окна при клике на кнопку "Войти"
// document.getElementById('loginButton').addEventListener('click', function() {
//     document.getElementById('loginModal').style.display = 'block';
//   });

// // Скрытие модального окна при клике на крестик
// document.getElementsByClassName('close')[0].addEventListener('click', function() {
//   document.getElementById('loginModal').style.display = 'none';
// });

// // Обработка отправки формы
// document.getElementById('loginForm').addEventListener('submit', function(event) {
//   event.preventDefault(); // Предотвращаем отправку формы по умолчанию
//   // Здесь вы можете добавить код для отправки данных формы на сервер и обработки результата
//   // Например, с использованием AJAX запроса
//   // После успешной авторизации скрываем модальное окно
//   document.getElementById('loginModal').style.display = 'none';
//   // Здесь также можно обновить интерфейс, например, отобразить имя пользователя в углу
// });
//

//   function openTab(evt, tabName) {
//     var tabcontent = document.getElementsByClassName("tabcontent");
//     for (var i = 0; i < tabcontent.length; i++) {
//         tabcontent[i].classList.remove("active");
//         tabcontent[i].classList.remove("active-prev");
//     }
//
//     var currentTab = document.getElementById(tabName);
//     currentTab.classList.add("active");
//
//     var prevTabName = (tabName === 'Login') ? 'Register' : 'Login';
//     var prevTab = document.getElementById(prevTabName);
//     prevTab.classList.add("active-prev");
//
//     var tablinks = document.getElementsByClassName("tablinks");
//     for (var i = 0; i < tablinks.length; i++) {
//         tablinks[i].classList.remove("active");
//     }
//     evt.currentTarget.classList.add("active");
// }
//
// var tabLinks = document.getElementsByClassName("tablinks");
// for (var i = 0; i < tabLinks.length; i++) {
//     tabLinks[i].addEventListener("click", function(event) {
//         openTab(event, this.textContent.trim());
//     });
// }


function smoothScroll(target) {
    // Получаем элемент, к которому нужно прокрутить
    var targetElement = document.querySelector(target);

    // Получаем текущую позицию прокрутки
    var startPosition = window.pageYOffset;

    // Вычисляем расстояние до целевого элемента
    var targetPosition = targetElement.offsetTop;

    // Вычисляем расстояние, на которое нужно прокрутить
    var distance = targetPosition - startPosition;

    // Устанавливаем длительность анимации и скорость прокрутки
    var duration = 300;
    var start = null;

    // Функция анимации прокрутки
    function scrollAnimation(timestamp) {
        if (!start) start = timestamp;

        // Вычисляем время, прошедшее с начала анимации
        var progress = timestamp - start;

        // Вычисляем новую позицию прокрутки
        var currentPosition = Math.min(progress / duration, 1) * distance + startPosition;

        // Прокручиваем страницу к новой позиции
        window.scrollTo(0, currentPosition);

        // Продолжаем анимацию, пока не достигнем конечной позиции
        if (progress < duration) {
            window.requestAnimationFrame(scrollAnimation);
        }
    }

    // Запускаем анимацию прокрутки
    window.requestAnimationFrame(scrollAnimation);
}


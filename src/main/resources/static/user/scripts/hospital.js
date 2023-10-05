$(document).ready(function(){
    hospitalDetails()
})

function hospitalDetails(){
    const url = window.location.href; // 현재 페이지의 URL을 가져옵니다.
    const urlParts = url.split('/'); // URL을 "/"로 분할합니다.
    const seq = urlParts[urlParts.length - 1]; // 배열의 마지막 요소를 가져옵니다.

    axios({
        method: 'get',
        url: '/user/hospital/'+seq,
        headers: {
            'Content-Type': 'application/json',
        }
    }).then((res) => {
        console.log(res.data.data)
    }).catch((error) => {
        console.log(error);
    })

}
// function hospitalSearchList(data){
//
//     for (const i in data) {
//         const setHtml =
//             `
//             <li class="hospitalDetail" seq="${data[i].SEQ}">
//                 <a href="hospital/${data[i].SEQ}" seq="${data[i].SEQ}">
//                     <div class="tags" seq="${data[i].SEQ}">
//                         <div class="tag blue">접수</div>
//                         <div class="tag sky">예약</div>
//                         <div class="tag green">비대면</div>
//                     </div>
//                     <h3>${data[i].NAME}</h3>
//                     <div class="star" seq="${data[i].SEQ}">
//                         <i>
//                             <img th:src="@{/user/images/star.png}" alt="">
//                         </i>
//                         <p seq="${data[i].SEQ}">5.0 <span>(재방문 90%)</span></p>
//                     </div>
//                     <div class="info" seq="${data[i].SEQ}">
//                         <h5>금요일 09:00~16:00</h5>
//                         <div class="line"></div>
//                         <span>이비인후과</span>
//                     </div>
//                     <div class="info" seq="${data[i].SEQ}">
//                         <i><img th:src="@{/user/images/loca.png}" alt=""></i>
//                         <h5>6Km</h5>
//                         <div class="line"></div>
//                         <span>서울시 강남구 신사동</span>
//                     </div>
//                 </a>
//             </li>
//             `
//         $('.item-append').append(setHtml);
//     }
//
//     if(data.length == 0){
//         const setHtml =
//             `
//                 <li>
//                     <a href="hospital">
//                         <div class="tags">
//                             <div class="tag blue">접수</div>
//                             <div class="tag sky">예약</div>
//                             <div class="tag green">비대면</div>
//                         </div>
//                         <h3>연세 이비인후과의원</h3>
//                         <div class="star">
//                             <i>
//                                 <img th:src="@{/user/images/star.png}" alt="">
//                             </i>
//                             <p>5.0 <span>(재방문 90%)</span></p>
//                         </div>
//                         <div class="info">
//                             <h5>금요일 09:00~16:00</h5>
//                             <div class="line"></div>
//                             <span>이비인후과</span>
//                         </div>
//                         <div class="info">
//                             <i><img th:src="@{/user/images/loca.png}" alt=""></i>
//                             <h5>6Km</h5>
//                             <div class="line"></div>
//                             <span>서울시 강남구 신사동</span>
//                         </div>
//                     </a>
//                 </li>
//         `
//         $('.item-append').append(setHtml);
//     }
// }
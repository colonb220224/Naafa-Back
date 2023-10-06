// $(document).ready(function(){
//     hospitalDetails()
// })

// function hospitalDetails(){
//     const url = window.location.href; // 현재 페이지의 URL을 가져옵니다.
//     const urlParts = url.split('/'); // URL을 "/"로 분할합니다.
//     const seq = urlParts[urlParts.length - 1]; // 배열의 마지막 요소를 가져옵니다.
//
//     axios({
//         method: 'get',
//         url: '/user/hospital/'+seq,
//         headers: {
//             'Content-Type': 'application/json',
//         }
//     }).then((res) => {
//         $('.sub-name-append').empty();
//         $('.main-name-append').empty();
//         $('.address-append').empty();
//         hospitalDetailsMain(res.data.data)
//     }).catch((error) => {
//         console.log(error);
//     })
//
// }
// function hospitalDetailsMain(data){
//
//     const setHtml1 =
//             `
//                 <h5>${data.NAME}</h5>
//             `
//     const setHtml2 =
//         `
//                 <h2>${data.NAME}</h2>
//             `
//     const setHtml3 =
//             `
//                 ${data.ADDRESS}
//             `
//     $('.sub-name-append').append(setHtml1);
//     $('.main-name-append').append(setHtml2);
//     $('.address-append').append(setHtml3);
//
// }
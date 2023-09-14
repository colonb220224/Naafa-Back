$(document).ready(function(){
    reload()
})


/**
 *  구성원 관리 회원 정보 추가 모달에서 저장 버튼 클릭시
 */
$(document).on('click', '#patientAdd', (e) =>{

    if($('#name').val() == ''){
        alert('회원명을 입력해주세요.')
        return;
    }

    if($('#socialNumberFirst').val() == ''){
        alert('주민등록번호를 입력해주세요.')
        return;
    }

    if($('#socialNumberSecond').val() == ''){
        alert('주민등록번호를 입력해주세요.')
        return;
    }

    if($('#relate').val() == ''){
        alert('본인과의 관게를 선택해주세요.')
        return;
    }

    if (!$('#agree1').is(':checked')) {
        alert('필수 항목에 동의하여야 합니다.')
        return;
    }

    if (!$('#agree2').is(':checked')) {
        alert('필수 항목에 동의하여야 합니다.')
        return;
    }

    axios({
        method: 'post',
        url: '/user/auth/patient/add',
        data: {
            "name" : $('#name').val(),
            "socialNumber" : $('#socialNumberFirst').val() + "-" + $('#socialNumberSecond').val(),
            "relate" : $('#relate').val()
        },
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Authorization' : 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWVfaHdpQG5hdmVyLmNvbSIsInJvbGUiOiJST0xFX1VTRVIiLCJzb2NpYWxUeXBlIjoiREVGQVVMVCIsImlhdCI6MTY5NDY2NzM2MiwiZXhwIjoxNjk0Njc4MTYyfQ.JTM0hpvyOV4ftLwZqVrL1EecTEuSNf1jezXCrJw49eQ'
        }
    }).then((res) => {
        console.log(res)
        alert(res.data.message)
        $('.modal').addClass("hidden");
        reload()
    }).catch((error) => {
        console.log(error);
        alert(error.response.data.message)
    })

})

/**
 *  구성원 관리 삭제 버튼 클릭시
 */
$(document).on('click', '#patientRemove', (e) =>{

    axios({
        method: 'delete',
        url: '/user/auth/patient/remove/' + $(e.target).attr('seq'),
        headers: {
            'Content-Type': 'application/json',
            'Authorization' : 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWVfaHdpQG5hdmVyLmNvbSIsInJvbGUiOiJST0xFX1VTRVIiLCJzb2NpYWxUeXBlIjoiREVGQVVMVCIsImlhdCI6MTY5NDY2NzM2MiwiZXhwIjoxNjk0Njc4MTYyfQ.JTM0hpvyOV4ftLwZqVrL1EecTEuSNf1jezXCrJw49eQ'
        }
    }).then((res) => {
        console.log(res)
        alert(res.data.message)
        reload()
    }).catch((error) => {
        console.log(error);
        alert(error.response.data.message)
    })

})

function reload(){

    axios({
        method: 'get',
        url: '/user/auth/patient/list',
        headers: {
            'Content-Type': 'application/json',
            'Authorization' : 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWVfaHdpQG5hdmVyLmNvbSIsInJvbGUiOiJST0xFX1VTRVIiLCJzb2NpYWxUeXBlIjoiREVGQVVMVCIsImlhdCI6MTY5NDY2NzM2MiwiZXhwIjoxNjk0Njc4MTYyfQ.JTM0hpvyOV4ftLwZqVrL1EecTEuSNf1jezXCrJw49eQ'
        }
    }).then((res) => {
        if(res.data.success){
            $('.item-append').empty();
            $('.img-append').empty();
            drawList(res.data.data)
        }
    }).catch((error) => {
        console.log(error);
    })

}
function drawList(data){

    for (const i in data) {
        const setHtml =
            `
            <div class="member_con">
               <div class="txt">
                    <span>${data[i].NAME}</span><br/>
                    <span>${data[i].SOCIAL_NUMBER}</span>
                </div>
                <ul class="btn">
                    <li>
                        <a href="./my_edit_member">
                            수정
                        </a>
                    </li>
                    <li>
                        <a href="#" id="patientRemove" seq="${data[i].SEQ}">
                            삭제
                        </a>
                    </li>
                 </ul>       
             </div> 
            `
        $('.item-append').append(setHtml);
    }

    if(data.length == 0){
        const setImg =
            `
            <div class="img">
                <!-- 구성원이 하나도 없는 경우 아래 이미지 노출 -->
                <img src="/user/images/add_grey.png" alt="">
            </div>
            <div class="txt">
                <p>
                    등록된 구성원이 없습니다                  
                </p>
            </div>
        `
        $('.img-append').append(setImg);
    }
    const setImg =
        `
            <div class="img">
                <!-- 구성원이 한명이라도 있는 경우 아래 이미지 노출 -->
                <img src="/user/images/add.png" alt="">
            </div>
        `
    $('.img-append').append(setImg);
}

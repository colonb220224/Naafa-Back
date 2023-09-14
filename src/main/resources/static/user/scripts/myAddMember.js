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
        location.href="/my_add_member";
    }).catch((error) => {
        console.log(error);
        alert(error.response.data.message)
    })

})

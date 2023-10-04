$(document).ready(function(){
    main()
})

function main(){

    axios({
        method: 'get',
        url: '/user/hospital/list',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then((res) => {
        if(res.data.success){
            $('.item-append').empty();
            hospitalList(res.data.data)
        }
    }).catch((error) => {
        console.log(error);
    })

}
function hospitalList(data){

    for (const i in data) {
        const setHtml =
            `
            <h5>        
                ${data[i].NAME}
            </h5>
            `
        $('.item-append').append(setHtml);
    }

    if(data.length == 0){
        const setHtml =
            `
            <h5>        
                연세정형외과
            </h5>
        `
        $('.item-append').append(setHtml);
    }
}

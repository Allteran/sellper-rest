var nomenclatureApi = Vue.resource('/nomenclature/{id}')


// Vue.component('nom-row', {
//     props: ['nom'],
//     template:
//         '<tr>'+
//             '<th scope="row"> {{ nom.name }}</th>' +
//             '<td>{{ nom.price }}</td>'+
//         '</tr>'
// });

Vue.component('nom-list', {
    props: ['nomList'],
    template:
        '<table class="table">' +
            '<thead>' +
                '<th scope="col">Наименование</th>' +
                '<th scope="col">Цена</th>' +
            '</thead>' +
            '<tbody>' +
                '<tr v-for="nom in nomList" :key="nom.id" :nomList="nomList">' +
                    '<td scope="row">{{ nom.name }}</td>' +
                    '<td>{{ nom.price }}</td>' +
                '</tr> ' +
            '</tbody>' +
        '</table>',
    created: function () {
        nomenclatureApi.get().then(result =>
            result.json().then(data =>
                data.forEach(nom => this.nomList.push(nom)
                )
            )
        )
    }
});


var nomTable = new Vue({
    el: '#nomTable',
    template: '<nom-list :nomList="nomList"/>',
    data: {
        nomList: []
    },
});


import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'

Vue.use(VueResource)

new Vue({
    el: "#app",
    render: a => a(App)
})

// /* Websocket */
// var stompClient = null;
//
// function connect() {
//     const socket = new SockJS('/gs-guide-websocket');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, frame => {
//         console.log('Connected: ' + frame);
//         stompClient.subscribe('/topic/activity', nomenclature => {
//             // showGreeting(JSON.parse(greeting.body).content);
//         });
//     });
// }
//
// function disconnect() {
//     if (stompClient !== null) {
//         stompClient.disconnect();
//     }
//     setConnected(false);
//     console.log("Disconnected");
// }
//
// function createNomenclature(nomenclature) {
//     stompClient.send("/app/changeNomenclature", {}, JSON.stringify(nomenclature));
// }
// /* Websocket ends*/
//
// var nomenclatureApi = Vue.resource('/no     System Settings → Workspace → Shortcuts Scroll through the list on the left under “System Services” until you find “KWin”. Click on that, and then scroll down in the list on the right until you see… :arrow_down: Switch one desktop to the left Switch one desktop to the right … and a little farther down… :arrow_down: … menclature/{id}')
//
// Vue.component('nom-list', {
//     props: ['nomList'],
//     template:
//         '<table class="table">' +
//         '<thead>' +
//         '<th scope="col">Наименование</th>' +
//         '<th scope="col">Цена</th>' +
//         '</thead>' +
//         '<tbody>' +
//         '<tr v-for="nom in nomList" :key="nom.id" :nomList="nomList">' +
//         '<td scope="row">{{ nom.name }}</td>' +
//         '<td>{{ nom.price }}</td>' +
//         '</tr> ' +
//         '</tbody>' +
//         '</table>',
//     created: function () {
//         nomenclatureApi.get().then(result =>
//             result.json().then(data =>
//                 data.forEach(nom => this.nomList.push(nom)
//                 )
//             )
//         )
//     }
// });


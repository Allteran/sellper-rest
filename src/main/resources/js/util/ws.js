// import SockJS from 'sockjs-client'
// import { Stomp } from '@stomp/stompjs'
//
// let stompClient = null
// const handlers = []
//
// export function connect() {
//     const socket = new SockJS('/gs-guide-websocket')
//     stompClient = Stomp.over(socket)
//     stompClient.debug = () => { }
//     stompClient.connect({}, frame => {
//         console.log('Connected: ' + frame)
//         stompClient.subscribe('/topic/activity', nomenclature => {
//             // showGreeting(JSON.parse(greeting.body).content)
//             handlers.forEach(handler => handler(JSON.parse(nomenclature.body)))
//         })
//     })
// }
//
// export function addHandler(handler) {
//     handlers.push(handler)
// }
//
// export function disconnect() {
//     if (stompClient !== null) {
//         stompClient.disconnect()
//     }
//     console.log("Disconnected")
// }
//
// export function sendNomenclature(nomenclature) {
//     stompClient.send("/app/changeNomenclature", {}, JSON.stringify(nomenclature))
// }
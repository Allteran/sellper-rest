var messageApi = Vue.resource('/message{/id}')

//nomenclature form
Vue.component('nom-form', {
})



function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('message-form', {
    props: ['messages', 'messageAttr'],
    data: function () {
        return {
            text: '',
            id: ''
        }
    },
    watch: {
        messageAttr: function (newVal, oldVal) {
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Write something" v-model="text"/>' +
        '<input type="button" value="save data" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var message = {text: this.text};
            if (this.id) {
                messageApi.update({id: this.id}, message).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.messages, data.id);
                        this.messages.splice(index, 1, data);
                    })
                )
            } else {
                messageApi.save({}, message).then(result =>
                    result.json().then(data => {
                        this.messages.push(data);
                        this.text = '';
                    })
                );
            }
        }
    }
})


Vue.component('message-row', {
    props: ['message', 'editMethod', 'messages'],
    template: '<div>' +
        '<i>({{ message.id }})</i> {{ message.text }}' +
        '<span><input type="button" value="Edit message" @click="edit"/> </span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editMethod(this.message)
        }
    }
});

Vue.component('messages-list', {
    props: ['messages', 'editMessage'],
    data: function () {
        return {
            message: null
        }
    },
    template: '<div>' +
        '<message-form :messages="messages"/>' +
        '<message-row v-for="message in messages" :key="message.id" :messageAttr="message" :editMethod="editMethod"/>' +
        '</div>',
    created: function () {
        messageApi.get().then(result =>
            result.json().then(data =>
                data.forEach(message => this.messages.push(message))
            )
        )

    },
    methods: {
        editMethod: function (message) {
            this.message = message;
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages"/>',
    data: {
        messages: []
    }
})
const express = require('express'),
    http = require('http'),
    app = express(),
    server = http.createServer(app),
    io = require('socket.io').listen(server);
app.get('/', (req, res) => {

    res.send('Game Server is running on port 8080')
});

io.on('connection', (socket) => {

    console.log('user connected')

    
    socket.on('join',  (user, code) => {
        console.log(user + ' : has joined the game')

        socket.join(code)
    })
    
    socket.on('leave', (user, code) => {

        console.log(user + ' : has left')

        socket.leave(code)
    })


    socket.on('sendmovement', (movement, code) => {

        console.log('send movement')

        io.to(code).emit('receivemovement', movement)
    })

})

server.listen(8080, () => {

    console.log('Node app is running on port 8080')

})
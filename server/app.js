const express = require('express'),
    http = require('http'),
    app = express(),
    server = http.createServer(app),
    io = require('socket.io').listen(server);
app.get('/', (req, res) => {

    res.send('Game Server is running on port 8080')
});

io.on('connection', (socket) => {
    
    socket.on('join',  (code) => {
        socket.join(code)
    })
    
    socket.on('leave', (code) => {
        socket.leave(code)
    })

    socket.on('sendmovement', (movement, code) => {
        io.to(code).emit('receivemovement', movement)
    })

    socket.on('senddice', (dice, code) => {
        io.to(code).emit('receivedice', dice)
    })

    socket.on('sendmasterleave', (leave, code) => {
        io.to(code).emit('receivemasterleave', leave)
    })

})

server.listen(8080, () => {
    console.log('Node app is running on port 8080')
})
const express = require('express');
const cors = require('cors');
const  bodyParser  =  require('body-parser');
const config = require('./config.js');
var mongoose = require('mongoose');

const app = express();
const  router  =  express.Router();
app.use(cors());

mongoose.connect(config.collection, { useNewUrlParser: true, useUnifiedTopology: true, useFindAndModify: false })
    .then(() => console.log('connection successful'))
    .catch((err) => console.error(err));

router.use(bodyParser.urlencoded({ extended:  false }));
router.use(bodyParser.json());

// Message from pinging server
router.get('/', (req, res) => {
    res.status(200).send('This is an authentication server');
});

// Creates listener for http requests
app.use(router);
const  port  =  process.env.PORT  ||  3000;
app.listen(port, () => {
    console.log('Server listening at http://localhost:'  +  port);
});

var BookSchema = new mongoose.Schema({
    isbn: String,
    title: String,
    author: String,
    description: String,
    published_year: String,
    publisher: String,
    updated_date: {type: Date, default: Date.now},
});

const Book = mongoose.model('Books', BookSchema);

// --------------------------------------------------------
// Router functions
// --------------------------------------------------------

router.get('/getBooks', (req, res, next) => {
    Book.find(function (err, products) {
        if (err) return next(err);
        res.json(products);
    });
});

router.get('/getBook/:id', (req, res, next) => {
    Book.findById(req.params.id, function (err, post) {
        if (err) return next(err);
        res.json(post);
    });
});

router.post('/createBook', (req, res, next) => {
    Book.create(req.body, function (err, post) {
        if (err) return next(err);
        res.json(post);
    });
});

router.put('/updateBook/:id', (req, res, next) => {
    const book = {
        isbn: req.body.isbn,
        title: req.body.title,
        author: req.body.author,
        description: req.body.description,
        published_year: req.body.published_year,
        publisher: req.body.publisher,
        updated_date: Date.now(),
    };
    Book.findByIdAndUpdate(req.params.id, book, function (err, post){
        if (err) return next(err);
        res.json(post);
    });
});

router.delete('/deleteBook/:id', (req, res, next) => {
    Book.findByIdAndDelete(req.params.id, function (err, post){
        if (err) return next(err);
        res.json(post);
    })
});

const express = require('express')
const cors = require('cors')
const { Pool } = require('pg')
require('dotenv').config()

const PORT = 5000

const pool = new Pool({
    connectionString: process.env.DATABASE_URL
})


const connDB = async () => {
    console.log('Проверка запуска БД...')

    try {
        const db = await pool.query('SELECT * FROM pg_catalog.pg_tables')
        console.log('БД успешно подключена')
    } catch(e) {
        console.log('Ошибка подключения БД!', e);
        process.exit(1);
    }

    // SELECT * FROM pg_catalog.pg_tables
    // 'БД успешно подключена'
}

const app = express()

app.use(express.json())
app.use(cors())

app.get('/', (req, res) => {
    res.json(`Server started ${PORT}`)
})

app.get('/users', async (req, res) => {
    try {
        const { rows } = await pool.query('SELECT * FROM users')
        return res.status(200).send(rows)
    } catch (err) {
        return res.status(400).send(err)
    }
})

const start = async () => {
    try {
        await connDB();

        console.log(`Начало запуска сервера на порту ${PORT}...`);

        app.listen(PORT, () => console.log(`Сервер работает на порту ${PORT}`))
    } catch (err) {
        console.log(err)
    }
}

start()


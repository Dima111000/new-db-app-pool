const express = require('express')
const cors = require('cors')
const { Pool } = require('pg')

const PORT = 5000

const pool = new Pool({
    connectionString: 'postgres://oswgfrke:9rDeFx9-Z4r7n8QP0iXqwZgHeewlI98n@mouse.db.elephantsql.com/oswgfrke'
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
}

const app = express()

app.use(express.json())
app.use(cors())

app.get('/', (req, res) => {
    try {
        res.send(`
        <div style="padding: 5px 45px>
            <h1>Лабораторная работа №2</h1>
            <nav>
                <ul style="display: flex; gap: 10px; flex-direction: column; padding: 0">
                    <li style="list-style:none">
                        <button style="padding: 10px; text-align:center; background-color: green; outline: none; border: none; border-radius: 5px; width: 250px"><a href="/require-db" style="text-decoration:none; color:#fff; font-size: 18px; font-weight: 500">Проверка подключения БД</a></button>
                    </li>
                    <li style="list-style:none">
                        <button style="padding: 10px; text-align:center; background-color: green; outline: none; border: none; border-radius: 5px; width: 250px"><a href="/users" style="text-decoration:none; color:#fff; font-size: 18px; font-weight: 500">Вывод всех данных с таблицы users</a></button>
                    </li>
                    <li style="list-style:none">
                        <button style="padding: 10px; text-align:center; background-color: green; outline: none; border: none; border-radius: 5px; width: 250px"><a href="/all-schemas" style="text-decoration:none; color:#fff; font-size: 18px; font-weight: 500">Вывод всех баз данных</a></button>
                    </li>
                    <li style="list-style:none">
                        <button style="padding: 10px; text-align:center; background-color: green; outline: none; border: none; border-radius: 5px; width: 250px"><a href="/all-tables" style="text-decoration:none; color:#fff; font-size: 18px; font-weight: 500">Вывод всех таблиц</a></button>
                    </li>
                    <li style="list-style:none">
                        <button style="padding: 10px; text-align:center; background-color: green; outline: none; border: none; border-radius: 5px; width: 250px"><a href="/all-views" style="text-decoration:none; color:#fff; font-size: 18px; font-weight: 500">Вывод всех представлений</a></button>
                    </li>
                    <li style="list-style:none">
                        <button style="padding: 10px; text-align:center; background-color: green; outline: none; border: none; border-radius: 5px; width: 250px"><a href="/all-triggers" style="text-decoration:none; color:#fff; font-size: 18px; font-weight: 500">Вывод всех триггеров</a></button>
                    </li>
                    <li style="list-style:none">
                        <button style="padding: 10px; text-align:center; background-color: green; outline: none; border: none; border-radius: 5px; width: 250px"><a href="/all-procedures" style="text-decoration:none; color:#fff; font-size: 18px; font-weight: 500">Вывод всех процедур</a></button>
                    </li>
                    <li style="list-style:none">
                        <button style="padding: 10px; text-align:center; background-color: green; outline: none; border: none; border-radius: 5px; width: 250px"><a href="/get-index" style="text-decoration:none; color:#fff; font-size: 18px; font-weight: 500">Вывод индексов для таблицы Users</a></button>
                    </li>
                </ul>
            </nav>
        </div>
    `)
    } catch(e) {
        console.log(e);
        res.json('[]')
    }
})

app.get('/require-db', (req, res) => {
    res.json(`БД подключена успешно`)
})


app.get('/users', async (req, res) => {
    try {
        const { rows } = await pool.query('SELECT * FROM users')
        return res.status(200).send(rows)
    } catch (err) {
        return res.status(400).send(err)
        res.json('[]')
    }
})

app.get('/all-schemas', async (req, res) => {
    try {
        //SELECT datname FROM pg_database
        const all = await pool.query(`SELECT datname FROM pg_database`)
        return res.json(all)
    } catch(e) {
        console.log('Ошибка вывода всех баз данных', e);
        res.json('[]')
    }
})

app.get('/all-tables', async (req, res) => {
    try {
        const all = await pool.query(`SELECT tablename from pg_tables WHERE schemaname='public'`)
        return res.json(all)
    } catch(e) {
        console.log('Ошибка вывода всех таблиц', e);
        res.json('[]')
    }
})

app.get('/all-views', async (req, res) =>{
    try {
        const all = await pool.query(`select table_name from INFORMATION_SCHEMA.views`)
        return res.json(all)
    } catch(e) {
        console.log('Ошибка вывода всех представлений', e);
        res.json('[]')
    }
})

app.get('/all-triggers', async (req, res) =>{
    try {
        const all = await pool.query(`SELECT evtname as "Name", evtevent as "Event", pg_catalog.pg_get_userbyid(e.evtowner) as "Owner",
        case evtenabled when 'O' then 'enabled'  when 'R' then 'replica'  when 'A' then 'always'  when 'D' then 'disabled' end as "Enabled",
        e.evtfoid::pg_catalog.regproc as "Procedure", pg_catalog.array_to_string(array(select x from pg_catalog.unnest(evttags) as t(x)), ', ') as "Tags"
       FROM pg_catalog.pg_event_trigger e ORDER BY 1`)
        return res.json(all)
    } catch(e) {
        console.log('Ошибка вывода всех триггеров', e);
        res.json('[]')
    }
})

app.get('/all-procedures', async (req, res) =>{
    try {
        const all = await pool.query(`SELECT prosrc FROM pg_proc WHERE proname = 'function_name'`)
        return res.json(all)
    } catch(e) {
        console.log('Ошибка вывода всех процедур', e);
        res.json('[]')
    }
})

app.get('/get-index', async (req, res) =>{
    try {
        const all = await pool.query(`SELECT * FROM pg_indexes WHERE tablename = 'users'`)
        return res.json(all)
    } catch(e) {
        console.log('Ошибка вывода индекса', e);
        res.json('[]')
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


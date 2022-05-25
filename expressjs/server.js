import express from 'express';
import cors from 'cors';
import morgan from "morgan";

const app = express();

//! cors() => security stuff
const whitelist = [
    'http://127.0.0.1:3000',
    'http://localhost:3000',
  ]

const corsOptions = {
  origin: function (origin, callback) {
    if (whitelist.indexOf(origin) !== -1) {
      callback(null, true)
    } else {
      callback(new Error('Not allowed by CORS'))
    }
  }
}

app.options(whitelist, cors(corsOptions))
app.use(cors(corsOptions));

app.use(express.json());

// better logs
app.use(morgan("combined"));

//! ROUTING ENDPOINTS
app.get('/api/', (req, res) => {
  console.log("prendete lucesita")  
  res.json("hola")
});
app.get('/api/valvulas', (req, res) => {
    console.log("valvulaaaas")
    // 3 valvulas
    const valvulasEnviar = 
    [   //valvula 1
        [[42,53], [38, 12], [99,4], [52,65]],
        //valvula 2
        [[2,5], [8, 2], [9,49]],
        //valvula 3
        [[26,48], [87, 25], [29,64]]
    ];
    return res.status(200).json(valvulasEnviar);
});

app.post('/api/login', (req, res) => {
    console.log("logiiiin")
    const {userEmail, userPassword} = req.body;
    const response = `Te veo email:${userEmail} contraseña:${userPassword}`
    return res.status(200).json(response);
});


//! SERVER INITILIZATION
const PORT = process.env.PORT || 3001;
app.listen(PORT, () => {
  console.log(`Server RDY. Listeting to ${PORT}`)
});


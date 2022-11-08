const shrinkMockDB = <T>(mockDB: T[]) => {
    const lenght = mockDB.length;
    mockDB = mockDB.slice(lenght-450, lenght);
}

const MockDB: IMedida[] = [];

interface IMedida {
    senial: number;
    fisico: number;
    timestamp: number;
}

class Medida {
    private senial: number;
    private fisico: number;
    private timestamp: number;
    
    public constructor(min:number, max:number) {
        const datoSenial = this.getRandomInteger(max, min);
        this.senial = datoSenial * 1.44928924;
        this.fisico = datoSenial * 2.393819;
        this.timestamp = Date.now();
    }
    private getRandomInteger (min:number, max:number): number {
        return Math.floor(Math.random() * (max-min)) + min;
    }
    public getMedidaObject (): IMedida {
        return {
            senial: this.senial,
            fisico: this.fisico,
            timestamp: this.timestamp
        }
    }
}

export const populateMockDB = () => {
    const segundosIntervalo = 2;
    setInterval(()=> {
        MockDB.push(new Medida(50, 100).getMedidaObject());
    }, segundosIntervalo * 1000);
}

export const getUltimosDatosSimulacion = (): IMedida[]  => {
    const arrLength = MockDB.length;
    const rangeLength = 10; //last 10 items
    const startIndex = (arrLength - rangeLength > 0) ? arrLength - rangeLength : 0;
    
    if (arrLength >= 500) shrinkMockDB(MockDB);

    return MockDB.slice(startIndex, arrLength);
}

export const getDatosSimulacion = (): IMedida[] => {
    if (MockDB.length >= 500) shrinkMockDB(MockDB);
    return MockDB;
}

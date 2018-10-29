export interface ICadastro {
    id?: number;
    nome?: string;
    email?: string;
    gifContentType?: string;
    gif?: any;
}

export class Cadastro implements ICadastro {
    constructor(public id?: number, public nome?: string, public email?: string, public gifContentType?: string, public gif?: any) {}
}

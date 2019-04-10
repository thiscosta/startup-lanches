import { defineAction } from 'redux-define'
import { createAction } from 'redux-actions'

export const CARREGAR_LISTA_INGREDIENTES = defineAction('CARREGAR_LISTA_INGREDIENTES', ['START', 'SUCCESS'], 'CARREGAR A LISTA DE INGREDIENTES')
export const ALERTAR_ERRO = defineAction('ALERTAR_ERRO', ['ALERT', 'DISMISS'], 'ALERTAR O ERRO AO USUÁRIO')
export const ALTERAR_CONTEUDO_PRONTO_INGREDIENTE = defineAction('ALTERAR_CONTEUDO_PRONTO_INGREDIENTE', ['SWITCH'], 'ALTERAR CONTEUDO ESTA PRONTO DO REDUCER')

export const carregarListaIngredientes = createAction(CARREGAR_LISTA_INGREDIENTES.START)
export const carregouListaIngredientes = createAction(CARREGAR_LISTA_INGREDIENTES.SUCCESS)

export const alterarConteudoProntoIngrediente = createAction(ALTERAR_CONTEUDO_PRONTO_INGREDIENTE.SWITCH)

const initialState = {
    listaIngredientes: [],
    conteudoEstaPronto: true
}

export default function ingredientesReducer(state = initialState, action) {
    switch (action.type) {
        case CARREGAR_LISTA_INGREDIENTES.START:
            return {
                ...state,
                conteudoEstaPronto: false
            }
        case CARREGAR_LISTA_INGREDIENTES.SUCCESS:
            return {
                ...state,
                listaIngredientes: action.payload.ingredientes,
                conteudoEstaPronto: true
            }
        case ALTERAR_CONTEUDO_PRONTO_INGREDIENTE.SWITCH:
            return {
                ...state,
                conteudoEstaPronto: !state.conteudoEstaPronto
            }
        default:
            return state
    }
}
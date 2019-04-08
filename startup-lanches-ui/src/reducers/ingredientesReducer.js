import { defineAction } from 'redux-define'
import { createAction } from 'redux-actions'

export const CARREGAR_LISTA_INGREDIENTES = defineAction('CARREGAR_LISTA_INGREDIENTES', ['START', 'SUCCESS'], 'CARREGAR A LISTA DE INGREDIENTES')
export const ALERTAR_ERRO = defineAction('ALERTAR_ERRO', ['ALERT', 'DISMISS'], 'ALERTAR O ERRO AO USUÁRIO')

export const carregarListaIngredientes = createAction(CARREGAR_LISTA_INGREDIENTES.START)
export const carregouListaIngredientes = createAction(CARREGAR_LISTA_INGREDIENTES.SUCCESS)

export const alertarErro = createAction(ALERTAR_ERRO.ALERT)
export const fecharErro = createAction(ALERTAR_ERRO.DISMISS)

const initialState = {
    listaIngredientes: [],
    conteudoEstaPronto: true,
    erro: '',
    temErro: true
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
        case ALERTAR_ERRO.ALERT:
            return {
                ...state,
                conteudoEstaPronto: true,
                erro: action.payload.erro,
                temErro: true
            }
        case ALERTAR_ERRO.DISMISS:
            return {
                ...state,
                erro: '',
                temErro: false
            }
        default:
            return state
    }
}
import { defineAction } from 'redux-define'
import { createAction } from 'redux-actions'

export const ALERTAR_ERRO = defineAction('ALERTAR_ERRO', ['ALERT', 'DISMISS'], 'ALERTAR O ERRO AO USUÁRIO')

export const alertarErro = createAction(ALERTAR_ERRO.ALERT)
export const fecharErro = createAction(ALERTAR_ERRO.DISMISS)

const initialState = {
    erro: '',
    temErro: false
}

export default function mensagemReducer(state = initialState, action) {
    switch (action.type) {
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
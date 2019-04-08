import { defineAction } from 'redux-define'
import { createAction } from 'redux-actions'

export const CARREGAR_LISTA_COMPRAS = defineAction('CARREGAR_LISTA_COMPRAS', ['START', 'SUCCESS'], 'CARREGAR A LISTA DE COMPRAS')
export const COMPRAR_LANCHE = defineAction('COMPRAR_LANCHE', ['START', 'SUCCESS'], 'COMPRAR O LANCHE SELECIONADO')
export const ALERTAR_ERRO = defineAction('ALERTAR_ERRO', ['ALERT', 'DISMISS'], 'ALERTAR O ERRO AO USUÁRIO')

export const carregarListaCompras = createAction(CARREGAR_LISTA_COMPRAS.START)
export const carregouListaCompras = createAction(CARREGAR_LISTA_COMPRAS.SUCCESS)

export const comprarLanche = createAction(COMPRAR_LANCHE.START)
export const comprouLanche = createAction(COMPRAR_LANCHE.SUCCESS)

export const alertarErro = createAction(ALERTAR_ERRO.ALERT)
export const fecharErro = createAction(ALERTAR_ERRO.DISMISS)

const initialState = {
    listaCompras: [],
    conteudoEstaPronto: true,
    compraEfetuada: null,
    erro: '',
    temErro: true
}

export default function comprasReducer(state = initialState, action) {
    switch (action.type) {
        case CARREGAR_LISTA_COMPRAS.START:
        case COMPRAR_LANCHE.START:
            return {
                ...state,
                conteudoEstaPronto: false
            }
        case COMPRAR_LANCHE.SUCCESS:
            return {
                ...state,
                compraEfetuada: action.payload.compra,
                conteudoEstaPronto: true
            }
        case CARREGAR_LISTA_COMPRAS.SUCCESS:
            return {
                ...state,
                listaCompras: action.payload.compras,
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
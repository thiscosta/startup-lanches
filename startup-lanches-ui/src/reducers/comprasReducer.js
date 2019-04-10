import { defineAction } from 'redux-define'
import { createAction } from 'redux-actions'

export const CARREGAR_LISTA_COMPRAS = defineAction('CARREGAR_LISTA_COMPRAS', ['START', 'SUCCESS'], 'CARREGAR A LISTA DE COMPRAS')
export const COMPRAR_LANCHE = defineAction('COMPRAR_LANCHE', ['START', 'SUCCESS'], 'COMPRAR O LANCHE SELECIONADO')
export const ALERTAR_ERRO = defineAction('ALERTAR_ERRO', ['ALERT', 'DISMISS'], 'ALERTAR O ERRO AO USUÁRIO')
export const ALTERAR_CONTEUDO_PRONTO_COMPRA = defineAction('ALTERAR_CONTEUDO_PRONTO_COMPRA', ['SWITCH'], 'ALTERAR CONTEUDO ESTA PRONTO DO REDUCER')
export const carregarListaCompras = createAction(CARREGAR_LISTA_COMPRAS.START)
export const carregouListaCompras = createAction(CARREGAR_LISTA_COMPRAS.SUCCESS)

export const comprarLanche = createAction(COMPRAR_LANCHE.START)
export const comprouLanche = createAction(COMPRAR_LANCHE.SUCCESS)

export const alterarConteudoProntoCompra = createAction(ALTERAR_CONTEUDO_PRONTO_COMPRA.SWITCH)

const initialState = {
    listaCompras: [],
    conteudoEstaPronto: true,
    compraEfetuada: null,
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
        case ALTERAR_CONTEUDO_PRONTO_COMPRA.SWITCH:
            return {
                ...state,
                conteudoEstaPronto: !state.conteudoEstaPronto
            }
        default:
            return state
    }
}
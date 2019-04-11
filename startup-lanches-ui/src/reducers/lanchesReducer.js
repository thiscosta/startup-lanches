import { defineAction } from 'redux-define'
import { createAction } from 'redux-actions'

export const CARREGAR_LISTA_LANCHES = defineAction('CARREGAR_LISTA_LANCHES', ['START', 'SUCCESS'], 'CARREGA A LISTA DE LANCHES LANCHES')
export const COMPRAR_LANCHE = defineAction('COMPRAR_LANCHE', ['START', 'SUCCESS'], 'COMPRAR O LANCHE SELECIONADO')
export const SELECIONAR_LANCHE = defineAction('SELECIONAR_LANCHE', ['SELECT'], 'SELECIONAR LANCHE')
export const MODAL_CRIACAO = defineAction('MODAL_CRIACAO', ['TOGGLE'], 'MUDAR VISIBILIDADE DO MODAL DE CRIAÇÃO DE LANCHE')
export const VERIFICAR_EXISTENCIA_LANCHE = defineAction('VERIFICAR_EXISTENCIA_LANCHE', ['CHECK'], 'VERIFICA SE O LANCHE QUE FOI COMPRADO EXISTE')
export const ALTERAR_CONTEUDO_PRONTO_LANCHE = defineAction('ALTERAR_CONTEUDO_PRONTO_LANCHE', ['SWITCH'], 'ALTERAR CONTEUDO ESTA PRONTO DO REDUCER')

export const carregarListaLanches = createAction(CARREGAR_LISTA_LANCHES.START)
export const carregouListaLanches = createAction(CARREGAR_LISTA_LANCHES.SUCCESS)

export const selecionarLanche = createAction(SELECIONAR_LANCHE.SELECT)

export const mudarVisibilidadeCriacao = createAction(MODAL_CRIACAO.TOGGLE)

export const alterarConteudoProntoLanche = createAction(ALTERAR_CONTEUDO_PRONTO_LANCHE.SWITCH)

export const verificarLancheExiste = createAction(VERIFICAR_EXISTENCIA_LANCHE.CHECK)

const initialState = {
    listaLanches: [],
    lancheSelecionado: null,
    conteudoEstaPronto: true,
    estaCriando: false
}

export default function lanchesReducer(state = initialState, action) {
    switch (action.type) {
        case CARREGAR_LISTA_LANCHES.START:
            return {
                ...state,
                conteudoEstaPronto: false
            }
        case CARREGAR_LISTA_LANCHES.SUCCESS:
            return {
                ...state,
                listaLanches: action.payload.listaLanches,
                conteudoEstaPronto: true
            }
        case MODAL_CRIACAO.TOGGLE:
            return{
                ...state,
                estaCriando: !state.estaCriando
            }
        case SELECIONAR_LANCHE.SELECT:
            return {
                ...state,
                lancheSelecionado: action.payload.lanche,
                conteudoEstaPronto: true
            }
        case VERIFICAR_EXISTENCIA_LANCHE.CHECK:
            let lanche = state.listaLanches.filter((e) => {
                return action.payload.lanche.id === e.id
            })
            
            let novaListaLanches = state.listaLanches

            if(!lanche.length > 0){
                novaListaLanches.push(action.payload.lanche)
            }
            return {
                ...state,
                listaLanches: novaListaLanches,
                conteudoEstaPronto: true
            }
        case ALTERAR_CONTEUDO_PRONTO_LANCHE.SWITCH:
            return {
                ...state,
                conteudoEstaPronto: !state.conteudoEstaPronto
            }
        default:
            return state
    }
}
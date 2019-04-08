import Cardapio from './views/Cardapio'
import Historico from './views/Historico'

const routes = [
    {
        path: "/cardapio",
        component: Cardapio,
        name: 'Cardápio'
    },
    {
        path: "/historico",
        component: Historico,
        name: 'Histórico'
    }

]

export default routes
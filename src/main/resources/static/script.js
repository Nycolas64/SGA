const API_URL_AMBIENTES = 'http://localhost:8081/ambientes';
const API_URL_SOLICITACOES = 'http://localhost:8081/solicitacoes';
let ambienteEditando = null;
let solicitacaoAberta = null;
let visualizacaoLista = false;

async function carregarAmbientes() {
    try {
        const response = await fetch(API_URL_AMBIENTES);
        const ambientes = await response.json();
        const lista = document.getElementById('listaAmbientes');
        lista.innerHTML = '';
        ambientes.forEach(ambiente => {
            const eqTexto = ambiente.equipamentos && ambiente.equipamentos.length > 0 
                ? ambiente.equipamentos.map(e => e.nome).join(', ') 
                : 'Nenhum';
            const row = document.createElement('div');
            row.className = 'row-ambiente';
            row.innerHTML = `
                <span>${ambiente.nome}</span>
                <span>Capacidade ${ambiente.capacidade}, Equipamentos: ${eqTexto}.</span>
                <div class="card-actions">
                    <button class="btn-delete-row" onclick="deletarAmbiente(${ambiente.id})">Excluir</button>
                    <button class="btn-edit" onclick="prepararEdicao(${ambiente.id})">Editar</button>
                </div>
            `;
            lista.appendChild(row);
        });
    } catch (error) {
        console.error(error);
    }
}

function mostrarSecao(secao) {
    document.getElementById('secao-mapa').style.display = (secao === 'mapa') ? 'block' : 'none';
    document.getElementById('secao-solicitacoes').style.display = (secao === 'solicitacoes') ? 'block' : 'none';
    document.getElementById('btnNavMapa').classList.toggle('active', secao === 'mapa');
    document.getElementById('btnNavSolicitacoes').classList.toggle('active', secao === 'solicitacoes');
    if (secao === 'mapa') carregarAmbientes();
    if (secao === 'solicitacoes') carregarSolicitacoes('PENDENTE');
}

function alternarVisualizacao() {
    visualizacaoLista = !visualizacaoLista;
    const btnAtivo = document.querySelector('.tab-btn.active');
    const status = btnAtivo ? btnAtivo.innerText.toUpperCase() : 'PENDENTE';
    carregarSolicitacoes(status);
}

async function carregarSolicitacoes(status) {
    const botoes = document.querySelectorAll('.tab-btn');
    botoes.forEach(btn => {
        btn.classList.toggle('active', btn.innerText.toUpperCase().includes(status));
    });
    try {
        const response = await fetch(`${API_URL_SOLICITACOES}/filtro/${status}`);
        const dados = await response.json();
        const container = document.getElementById('listaSolicitacoes');
        container.innerHTML = '';
        if (dados.length === 0) {
            container.innerHTML = '<p class="msg-vazia">Vazio</p>';
            return;
        }

        if (visualizacaoLista) {
            container.className = "";
            let tabelaHtml = `
                <div style="overflow-x: auto; width: 100%; margin-top: 20px;">
                    <table class="solicitacoes-tabela" style="width: 100%; border-collapse: collapse; background-color: var(--white); border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
                        <thead>
                            <tr style="background-color: var(--unifil-orange); color: var(--white); text-align: left;">
                                <th style="padding: 12px 15px;">Solicitante</th>
                                <th style="padding: 12px 15px;">Sala</th>
                                <th style="padding: 12px 15px;">Data</th>
                                <th style="padding: 12px 15px;">Horário</th>
                            </tr>
                        </thead>
                        <tbody>
            `;
            dados.forEach(solicitacao => {
                const nomeUsuario = solicitacao.usuario ? solicitacao.usuario.nome : 'Desconhecido';
                tabelaHtml += `
                    <tr onclick="abrirModalDetalhes(${solicitacao.id})" style="border-bottom: 1px solid #ddd; cursor: pointer; transition: background 0.2s;">
                        <td style="padding: 12px 15px; font-weight: bold; color: var(--unifil-dark);">${nomeUsuario}</td>
                        <td style="padding: 12px 15px;">${solicitacao.ambiente.nome}</td>
                        <td style="padding: 12px 15px;">${solicitacao.data}</td>
                        <td style="padding: 12px 15px;">${solicitacao.horarioInicio} - ${solicitacao.horarioFim}</td>
                    </tr>
                `;
            });
            tabelaHtml += `</tbody></table></div>`;
            container.innerHTML = tabelaHtml;
        } else {
            container.className = "cards-grid";
            dados.forEach(solicitacao => {
                const nomeUsuario = solicitacao.usuario ? solicitacao.usuario.nome : 'Desconhecido';
                const card = `
                    <div class="card" onclick="abrirModalDetalhes(${solicitacao.id})">
                        <h3>${nomeUsuario}</h3>
                        <p>Sala: ${solicitacao.ambiente.nome}</p>
                        <p>Data: ${solicitacao.data}</p>
                        <p>Horário: ${solicitacao.horarioInicio} - ${solicitacao.horarioFim}</p>
                    </div>
                `;
                container.innerHTML += card;
            });
        }
    } catch (error) {
        console.error(error);
    }
}

async function abrirModalDetalhes(id) {
    try {
        const response = await fetch(`${API_URL_SOLICITACOES}/${id}`);
        const solicitacao = await response.json();
        solicitacaoAberta = id;
        document.getElementById('detalheNomeProfessor').innerText = solicitacao.usuario ? solicitacao.usuario.nome : 'Desconhecido';
        document.getElementById('detalheSala').innerText = solicitacao.ambiente.nome;
        document.getElementById('detalheData').innerText = solicitacao.data;
        document.getElementById('detalheHorario').innerText = `${solicitacao.horarioInicio} - ${solicitacao.horarioFim}`;
        document.getElementById('detalhePublico').innerText = solicitacao.publicoEsperado;
        document.getElementById('detalheMotivo').innerText = solicitacao.motivo;
        document.getElementById('detalheDescricao').innerText = solicitacao.descricao || 'Sem descrição';
        document.getElementById('modalDetalhesSolicitacao').style.display = 'flex';
    } catch (error) {
        console.error(error);
    }
}

function fecharModalDetalhes() {
    document.getElementById('modalDetalhesSolicitacao').style.display = 'none';
}

async function atualizarStatus(novoStatus) {
    try {
        const response = await fetch(`${API_URL_SOLICITACOES}/${solicitacaoAberta}/status`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novoStatus)
        });

        if (!response.ok) {
            const erro = await response.text();
            alert("AVISO DO SISTEMA: " + erro);
            return;
        }

        alert("Sucesso! O status foi atualizado.");
        fecharModalDetalhes();
        carregarSolicitacoes('PENDENTE');
    } catch (error) {
        console.error(error);
        alert("Erro técnico ao processar a solicitação.");
    }
}

function abrirModalAmbiente() {
    ambienteEditando = null;
    document.getElementById('modalAmbiente').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'ADICIONANDO NOVA SALA';
    document.getElementById('formAmbiente').reset();
    document.getElementById('btnExcluirModal').style.display = 'none';
}

function fecharModalAmbiente() {
    document.getElementById('modalAmbiente').style.display = 'none';
}

async function prepararEdicao(id) {
    try {
        const response = await fetch(`${API_URL_AMBIENTES}/${id}`);
        const ambiente = await response.json();
        ambienteEditando = id;
        document.getElementById('modalTitle').innerText = `EDITANDO: ${ambiente.nome}`;
        document.getElementById('campoNome').value = ambiente.nome;
        document.getElementById('campoCapacidade').value = ambiente.capacidade;
        const eqTexto = ambiente.equipamentos && ambiente.equipamentos.length > 0 
            ? ambiente.equipamentos.map(e => e.nome).join(', ') 
            : '';
        document.getElementById('campoEquipamentos').value = eqTexto;
        document.getElementById('campoDescricao').value = ambiente.descricao || '';
        document.getElementById('btnExcluirModal').style.display = 'block';
        document.getElementById('modalAmbiente').style.display = 'flex';
    } catch (error) {
        console.error(error);
    }
}

async function deletarAmbiente(id) {
    if (confirm('Deseja realmente excluir este ambiente?')) {
        try {
            await fetch(`${API_URL_AMBIENTES}/${id}`, { method: 'DELETE' });
            carregarAmbientes();
        } catch (error) {
            console.error(error);
        }
    }
}

async function deletarNoModal() {
    if (ambienteEditando) {
        await deletarAmbiente(ambienteEditando);
        fecharModalAmbiente();
    }
}

document.getElementById('formAmbiente').addEventListener('submit', async (e) => {
    e.preventDefault();
    const eqInput = document.getElementById('campoEquipamentos').value;
    const eqArray = eqInput.split(',').map(name => name.trim()).filter(name => name !== '').map(name => ({ nome: name }));

    const dados = {
        nome: document.getElementById('campoNome').value,
        capacidade: parseInt(document.getElementById('campoCapacidade').value),
        equipamentos: eqArray,
        descricao: document.getElementById('campoDescricao').value
    };
    const metodo = ambienteEditando ? 'PUT' : 'POST';
    const url = ambienteEditando ? `${API_URL_AMBIENTES}/${ambienteEditando}` : API_URL_AMBIENTES;
    try {
        await fetch(url, {
            method: metodo,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dados)
        });
        fecharModalAmbiente();
        carregarAmbientes();
    } catch (error) {
        console.error(error);
    }
});

window.onload = carregarAmbientes;

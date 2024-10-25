import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JOptionPane;

public class AlunoFormAWT extends Frame implements ActionListener {
    private TextField nomeField, enderecoField, idadeField;
    private Button cadastrarButton, limparButton, mostrarButton, sairButton;

    private List<Aluno> listaAlunos;

    public AlunoFormAWT() {
        listaAlunos = new ArrayList<>();

        // Painel principal
        Panel painelPrincipal = new Panel(new BorderLayout(10, 10));
        painelPrincipal.setBackground(Color.LIGHT_GRAY); // Definir fundo cinza claro para o painel principal

        // Painel com GridLayout 3x2 para o formulário
        Panel painelFormulario = new Panel(new GridLayout(3, 2, 10, 10));
        painelFormulario.setBackground(Color.LIGHT_GRAY); // Fundo cinza claro para o formulário

        // Campos do formulário
        painelFormulario.add(new Label("Nome:"));
        nomeField = new TextField(20);
        painelFormulario.add(nomeField);

        painelFormulario.add(new Label("Idade:"));
        idadeField = new TextField(3);
        painelFormulario.add(idadeField);

        painelFormulario.add(new Label("Endereço:"));
        enderecoField = new TextField(20);
        painelFormulario.add(enderecoField);

        // Painel com GridLayout 1x4 para os botões
        Panel painelBotoes = new Panel(new GridLayout(1, 4, 10, 10));
        painelBotoes.setBackground(Color.LIGHT_GRAY); // Fundo cinza claro para os botões

        cadastrarButton = new Button("Ok");
        limparButton = new Button("Limpar");
        mostrarButton = new Button("Mostrar");
        sairButton = new Button("Sair");

        painelBotoes.add(cadastrarButton);
        painelBotoes.add(limparButton);
        painelBotoes.add(mostrarButton);
        painelBotoes.add(sairButton);

        // Adicionando os subpainéis ao painel principal
        painelPrincipal.add(painelFormulario, BorderLayout.NORTH); // Adicionar formulário na parte superior
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH); // Adicionar botões na parte inferior

        // Adicionar painel principal ao frame
        add(painelPrincipal);

        // Configurar eventos dos botões
        cadastrarButton.addActionListener(this);
        limparButton.addActionListener(e -> limparCampos());
        mostrarButton.addActionListener(e -> mostrarListaAlunos());
        sairButton.addActionListener(e -> System.exit(0));

        // Configurações da janela
        setTitle("Cadastro de Aluno");
        setSize(400, 180);
        setVisible(true);

        // Fechar janela corretamente
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    // Limpar os campos do formulário
    private void limparCampos() {
        nomeField.setText(" ");
        enderecoField.setText(" " );
        idadeField.setText(" ");
    }

    // Mostrar pop-up com a lista de alunos cadastrados usando JOptionPane
    private void mostrarListaAlunos() {
        StringBuilder html = new StringBuilder("<html><table><tr><th>Resultado</th><th></th></tr>");
        for (Aluno aluno : listaAlunos) {
            html.append("<tr>Id: <td>").append(aluno.getUuid()).append("</td>Nome: <td>").append(aluno.getNome()).append("</td></tr>");
        }
        html.append("</table></html>");
   
        JOptionPane.showMessageDialog(this, html.toString(), "Lista de Alunos", JOptionPane.INFORMATION_MESSAGE);
    }

    // Ação do botão Cadastrar
    public void actionPerformed(ActionEvent ae) {
        try {
            String nome = nomeField.getText().trim();
            String endereco = enderecoField.getText().trim();
            if (nome.isEmpty() || endereco.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Erro: Nome e endereço não podem ser vazios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idade = Integer.parseInt(idadeField.getText());
            if (idade < 0 || idade > 120) {
                JOptionPane.showMessageDialog(this, "Erro: A idade deve ser um número entre 0 e 120.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UUID uuid = UUID.randomUUID();

            Aluno aluno = new Aluno();
            aluno.setNome(nome);
            aluno.setEndereco(endereco);
            aluno.setIdade(idade);
            aluno.setUuid(uuid);

            // Adicionar o aluno na lista
            listaAlunos.add(aluno);

            // Exibir uma mensagem informando que o aluno foi cadastrado
            JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso! UUID: " + aluno.getUuid().toString(), "Cadastro", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: A idade deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AlunoFormAWT();
    }
}
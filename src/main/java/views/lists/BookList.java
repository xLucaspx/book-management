package views.lists;

import controller.BookController;
import factory.ControllerFactory;
import models.Author;
import models.Book;
import models.Format;
import models.Publisher;
import models.filters.BookFilter;
import views.constants.Constants;
import views.details.BookDetails;
import views.forms.BookForm;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.Set;

import static utils.Lists.toSortedList;

public class BookList extends javax.swing.JInternalFrame {
	private final ControllerFactory controllerFactory;
	private final BookController bookController;

	private DefaultTableModel tableModel;
	private Set<Book> books;

	private final Runnable updateView = new Runnable() {
		@Override
		public void run() {
			search();
			fillTable();
			totalLabel.setText(String.format("Total encontrado: %d", books.size()));
		}
	};

	public BookList(ControllerFactory controllerFactory) {
		this.controllerFactory = controllerFactory;
		this.bookController = controllerFactory.getBookController();
		initComponents();
		updateView();
	}

	private void updateView() {
		SwingUtilities.invokeLater(updateView);
	}
	
		private void fillTable() {
		tableModel.setRowCount(0);

		if (books.isEmpty()) {
			bookTable.repaint();
			return;
		}

		var bookList = toSortedList(books);

		bookList.forEach(b -> tableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getFormat(),
			b.getPublisher(), b.getPages(), b.isRead() ? "Lido" : "Não lido"}));
	}

	private void search() {
		String input = searchInput.getText().trim();
		BookFilter filter = filterCombo.getItemAt(filterCombo.getSelectedIndex());

		if (input.isEmpty() || filter == null) {
			books = bookController.getAll();
			return;
		}

		books = bookController.filter(input, filter);
	}

		private Book getSelectedBook() {
		int selectedRow = bookTable.convertRowIndexToModel(bookTable.getSelectedRow());

		if (selectedRow == -1 || selectedRow >= tableModel.getRowCount())
			throw new RuntimeException("Você deve selecionar um livro!");

		var id = (int) tableModel.getValueAt(selectedRow, 0);
		return bookController.getById(id);
	}
	
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    title = new javax.swing.JLabel();
    tableScrollPane = new javax.swing.JScrollPane();
    String[] columnNames = {
      "ID",
      "Título",
      "Autor",
      "Formato",
      "Editora",
      "Nº páginas",
      "Status"
    };
    tableModel = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }

      @Override
      public Class<?> getColumnClass(int column) {
        return switch(column) {
          case 0, 5 -> Integer.class;
          case 2 -> Author.class;
          case 3 -> Format.class;
          case 4 -> Publisher.class;
          default -> String.class;
        };
      }
    };
    bookTable = new javax.swing.JTable();
    detailsButton = new javax.swing.JButton();
    editBtn = new javax.swing.JButton();
    statusBtn = new javax.swing.JButton();
    newBtn = new javax.swing.JButton();
    deleteBtn = new javax.swing.JButton();
    totalLabel = new javax.swing.JLabel();
    searchInput = new javax.swing.JTextField();
    filterLabel = new javax.swing.JLabel();
    var filtersComboModel = new DefaultComboBoxModel<>(models.filters.BookFilter.values());
    filterCombo = new javax.swing.JComboBox<>();
    searchLabel = new javax.swing.JLabel();
    searchButton = new javax.swing.JButton();
    clearFilterButton = new javax.swing.JButton();

    setBackground(Constants.BACKGROUND_COLOR);
    setBorder(new javax.swing.border.LineBorder(Constants.BLACK, 2, true));
    setClosable(true);
    setIconifiable(true);
    setMaximizable(true);
    setResizable(true);
    setTitle("Lista de livros");
    setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons/book-icon.png"))); // NOI18N
    setMinimumSize(null);
    setName("Lista de livros"); // NOI18N
    setNormalBounds(null);
    setVisible(true);
    addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
      public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
        formInternalFrameActivated(evt);
      }
      public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
      }
      public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
      }
      public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
      }
      public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
      }
      public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
      }
      public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
      }
    });

    title.setFont(Constants.TITLE_FONT);
    title.setForeground(Constants.FONT_COLOR);
    title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    title.setLabelFor(this);
    title.setText("Livros");
    title.setVerticalAlignment(javax.swing.SwingConstants.TOP);
    title.setFocusable(false);
    title.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    title.setMaximumSize(new java.awt.Dimension(650, 30));
    title.setMinimumSize(new java.awt.Dimension(650, 30));
    title.setName("Título"); // NOI18N
    title.setPreferredSize(new java.awt.Dimension(650, 30));
    title.setRequestFocusEnabled(false);
    title.setVerifyInputWhenFocusTarget(false);
    title.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

    tableScrollPane.setBackground(Constants.WHITE);
    tableScrollPane.setForeground(Constants.FONT_COLOR);
    tableScrollPane.setFocusable(false);
    tableScrollPane.setFont(Constants.DEFAULT_FONT);
    tableScrollPane.setMaximumSize(null);
    tableScrollPane.setMinimumSize(new java.awt.Dimension(840, 500));
    tableScrollPane.setName("Painel da tabela"); // NOI18N

    bookTable.setAutoCreateRowSorter(true);
    bookTable.setBackground(Constants.WHITE);
    bookTable.setFont(Constants.DEFAULT_FONT);
    bookTable.setForeground(Constants.FONT_COLOR);
    bookTable.setModel(tableModel);
    bookTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    bookTable.setGridColor(Constants.FONT_COLOR);
    bookTable.setIntercellSpacing(new java.awt.Dimension(5, 5));
    bookTable.setMaximumSize(null);
    bookTable.setMinimumSize(new java.awt.Dimension(840, 500));
    bookTable.setName("Lista de livros"); // NOI18N
    bookTable.setPreferredSize(null);
    bookTable.setRowHeight(25);
    bookTable.setSelectionBackground(Constants.DARK_GREEN);
    bookTable.setSelectionForeground(Constants.WHITE);
    bookTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    bookTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    bookTable.setShowGrid(false);
    bookTable.setShowHorizontalLines(true);
    bookTable.getTableHeader().setReorderingAllowed(false);
    TableColumn idColumn = bookTable.getColumnModel().getColumn(0);
    TableColumn titleColumn = bookTable.getColumnModel().getColumn(1);
    TableColumn authorColumn = bookTable.getColumnModel().getColumn(2);
    TableColumn formatColumn = bookTable.getColumnModel().getColumn(3);
    TableColumn publisherColumn = bookTable.getColumnModel().getColumn(4);
    TableColumn pagesColumn = bookTable.getColumnModel().getColumn(5);
    TableColumn statusColumn = bookTable.getColumnModel().getColumn(6);

    idColumn.setPreferredWidth(45);
    titleColumn.setPreferredWidth(202);
    authorColumn.setPreferredWidth(188);
    formatColumn.setPreferredWidth(105);
    publisherColumn.setPreferredWidth(155);
    pagesColumn.setPreferredWidth(73);
    statusColumn.setPreferredWidth(72);
    tableScrollPane.setViewportView(bookTable);

    detailsButton.setBackground(Constants.BURNT_YELLOW);
    detailsButton.setFont(Constants.LARGE_FONT);
    detailsButton.setForeground(Constants.WHITE);
    detailsButton.setText("Detalhes");
    detailsButton.setToolTipText("Visualizar detalhes do livro");
    detailsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    detailsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    detailsButton.setMaximumSize(new java.awt.Dimension(175, 40));
    detailsButton.setMinimumSize(new java.awt.Dimension(175, 40));
    detailsButton.setName("Botão de detalhes"); // NOI18N
    detailsButton.setPreferredSize(new java.awt.Dimension(175, 40));
    detailsButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        detailsButtonActionPerformed(evt);
      }
    });

    editBtn.setBackground(Constants.DARK_BLUE);
    editBtn.setFont(Constants.LARGE_FONT);
    editBtn.setForeground(Constants.WHITE);
    editBtn.setText("Editar");
    editBtn.setToolTipText("Abrir formulário de edição de livro");
    editBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    editBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    editBtn.setMaximumSize(new java.awt.Dimension(175, 40));
    editBtn.setMinimumSize(new java.awt.Dimension(175, 40));
    editBtn.setName("Botão de editar"); // NOI18N
    editBtn.setPreferredSize(new java.awt.Dimension(175, 40));
    editBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        editBtnActionPerformed(evt);
      }
    });

    statusBtn.setBackground(Constants.DARK_GREEN);
    statusBtn.setFont(Constants.LARGE_FONT);
    statusBtn.setForeground(Constants.WHITE);
    statusBtn.setText("Alterar status");
    statusBtn.setToolTipText("Alternar status do livro entre lido/não lido");
    statusBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    statusBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    statusBtn.setMaximumSize(new java.awt.Dimension(175, 40));
    statusBtn.setMinimumSize(new java.awt.Dimension(175, 40));
    statusBtn.setName("Botão de alterar status"); // NOI18N
    statusBtn.setPreferredSize(new java.awt.Dimension(175, 40));
    statusBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        statusBtnActionPerformed(evt);
      }
    });

    newBtn.setBackground(Constants.GREEN);
    newBtn.setFont(Constants.LARGE_FONT);
    newBtn.setForeground(Constants.WHITE);
    newBtn.setText("Novo");
    newBtn.setToolTipText("Abrir formulário de cadastro de livro");
    newBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    newBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    newBtn.setMaximumSize(new java.awt.Dimension(175, 40));
    newBtn.setMinimumSize(new java.awt.Dimension(175, 40));
    newBtn.setName("Botão novo livro"); // NOI18N
    newBtn.setPreferredSize(new java.awt.Dimension(175, 40));
    newBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        newBtnActionPerformed(evt);
      }
    });

    deleteBtn.setBackground(Constants.RED);
    deleteBtn.setFont(Constants.LARGE_FONT);
    deleteBtn.setForeground(Constants.WHITE);
    deleteBtn.setText("Excluir");
    deleteBtn.setToolTipText("Excluir o livro selecionado");
    deleteBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    deleteBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    deleteBtn.setMaximumSize(new java.awt.Dimension(175, 40));
    deleteBtn.setMinimumSize(new java.awt.Dimension(175, 40));
    deleteBtn.setName("Botão de excluir"); // NOI18N
    deleteBtn.setPreferredSize(new java.awt.Dimension(175, 40));
    deleteBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        deleteBtnActionPerformed(evt);
      }
    });

    totalLabel.setBackground(Constants.BACKGROUND_COLOR);
    totalLabel.setFont(Constants.MEDIUM_FONT);
    totalLabel.setForeground(Constants.FONT_COLOR);
    totalLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    totalLabel.setText("Total encontrado");
    totalLabel.setToolTipText("");
    totalLabel.setFocusable(false);
    totalLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    totalLabel.setMinimumSize(new java.awt.Dimension(70, 20));
    totalLabel.setName("Total registros"); // NOI18N
    totalLabel.setPreferredSize(new java.awt.Dimension(175, 20));

    searchInput.setBackground(Constants.WHITE);
    searchInput.setColumns(25);
    searchInput.setFont(Constants.DEFAULT_FONT);
    searchInput.setForeground(Constants.FONT_COLOR);
    searchInput.setHorizontalAlignment(javax.swing.JTextField.LEFT);
    searchInput.setToolTipText("Barra de pesquisa de livros");
    searchInput.setCaretColor(Constants.FONT_COLOR);
    searchInput.setMaximumSize(new java.awt.Dimension(270, 30));
    searchInput.setMinimumSize(new java.awt.Dimension(285, 30));
    searchInput.setName("Pesquisa"); // NOI18N
    searchInput.setPreferredSize(new java.awt.Dimension(285, 30));
    searchInput.setSelectedTextColor(Constants.WHITE);
    searchInput.setSelectionColor(Constants.DARK_BLUE);
    searchInput.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        searchInputKeyPressed(evt);
      }
    });

    filterLabel.setFont(Constants.DEFAULT_FONT);
    filterLabel.setForeground(Constants.FONT_COLOR);
    filterLabel.setText("Filtro");
    filterLabel.setFocusable(false);
    filterLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
    filterLabel.setMaximumSize(null);
    filterLabel.setMinimumSize(new java.awt.Dimension(195, 18));
    filterLabel.setName("Filter label"); // NOI18N
    filterLabel.setPreferredSize(new java.awt.Dimension(195, 18));
    filterLabel.setRequestFocusEnabled(false);
    filterLabel.setVerifyInputWhenFocusTarget(false);

    filterCombo.setBackground(Constants.WHITE);
    filterCombo.setFont(Constants.DEFAULT_FONT);
    filterCombo.setForeground(Constants.FONT_COLOR);
    filterCombo.setMaximumRowCount(10);
    filterCombo.setModel(filtersComboModel);
    filterCombo.setToolTipText("Selecione o filtro que será aplicado à pesquisa");
    filterCombo.setMaximumSize(new java.awt.Dimension(270, 30));
    filterCombo.setMinimumSize(new java.awt.Dimension(200, 30));
    filterCombo.setName("Filtro"); // NOI18N
    filterCombo.setPreferredSize(new java.awt.Dimension(200, 30));
    filterCombo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        filterComboActionPerformed(evt);
      }
    });

    searchLabel.setFont(Constants.DEFAULT_FONT);
    searchLabel.setForeground(Constants.FONT_COLOR);
    searchLabel.setText("Pesquisa");
    searchLabel.setFocusable(false);
    searchLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
    searchLabel.setMaximumSize(null);
    searchLabel.setMinimumSize(new java.awt.Dimension(280, 18));
    searchLabel.setName("Search label"); // NOI18N
    searchLabel.setPreferredSize(new java.awt.Dimension(280, 18));
    searchLabel.setRequestFocusEnabled(false);
    searchLabel.setVerifyInputWhenFocusTarget(false);

    searchButton.setBackground(null);
    searchButton.setFont(Constants.DEFAULT_FONT);
    searchButton.setForeground(Constants.FONT_COLOR);
    searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons/search.png"))); // NOI18N
    searchButton.setText("Pesquisar");
    searchButton.setToolTipText("Buscar livros a partir dos filtros selecionados");
    searchButton.setBorder(null);
    searchButton.setBorderPainted(false);
    searchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    searchButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    searchButton.setMargin(new java.awt.Insets(0, 0, 3, 0));
    searchButton.setMaximumSize(new java.awt.Dimension(150, 30));
    searchButton.setName("Botão pesquisar"); // NOI18N
    searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        searchButtonaddBtnMouseEntered(evt);
      }
      public void mouseExited(java.awt.event.MouseEvent evt) {
        searchButtonaddBtnMouseExited(evt);
      }
    });
    searchButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        searchButtonActionPerformed(evt);
      }
    });

    clearFilterButton.setBackground(null);
    clearFilterButton.setFont(Constants.DEFAULT_FONT);
    clearFilterButton.setForeground(Constants.FONT_COLOR);
    clearFilterButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons/clear-filter.png"))); // NOI18N
    clearFilterButton.setText("Limpar");
    clearFilterButton.setToolTipText("Limpar filtros");
    clearFilterButton.setBorder(null);
    clearFilterButton.setBorderPainted(false);
    clearFilterButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    clearFilterButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    clearFilterButton.setMargin(new java.awt.Insets(0, 0, 3, 0));
    clearFilterButton.setMinimumSize(new java.awt.Dimension(150, 30));
    clearFilterButton.setName("Botão limpar filtros"); // NOI18N
    clearFilterButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        clearFilterButtonaddBtnMouseEntered(evt);
      }
      public void mouseExited(java.awt.event.MouseEvent evt) {
        clearFilterButtonaddBtnMouseExited(evt);
      }
    });
    clearFilterButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        clearFilterButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap(15, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(searchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(25, 25, 25)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(filterCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(filterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(25, 25, 25)
            .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(25, 25, 25)
            .addComponent(clearFilterButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(167, 167, 167))
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        .addGap(25, 25, 25)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(editBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(detailsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(newBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(statusBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(deleteBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(0, 16, Short.MAX_VALUE))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(4, 4, 4)
            .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(25, 25, 25)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(detailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(editBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(statusBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(20, 20, 20)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(filterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(searchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(filterCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(clearFilterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(25, Short.MAX_VALUE))
    );

    searchInput.getAccessibleContext().setAccessibleName("Pesquisa");
    filterCombo.getAccessibleContext().setAccessibleName("Filtro");

    pack();
  }// </editor-fold>//GEN-END:initComponents

	private void showInternalFrame(JInternalFrame frame) {
		getDesktopPane().add(frame);
		frame.moveToFront();
		frame.requestFocus();
	}

	private void detailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsButtonActionPerformed
		try {
			var selectedBook = getSelectedBook();
			var view = new BookDetails(controllerFactory, selectedBook);
			showInternalFrame(view);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, String.format("Erro ao tentar abrir a página do livro:\n%s", e.getMessage()),
				getTitle(), JOptionPane.ERROR_MESSAGE);
		}
	}//GEN-LAST:event_detailsButtonActionPerformed

	private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
		try {
			var selectedBook = getSelectedBook();
			var form = new BookForm(controllerFactory, selectedBook);
			showInternalFrame(form);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
				String.format("Erro ao tentar abrir o formulário de edição de livro:\n%s", e.getMessage()), getTitle(),
				JOptionPane.ERROR_MESSAGE);
		}
	}//GEN-LAST:event_editBtnActionPerformed

	private void statusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusBtnActionPerformed
		try {
			var selectedBook = getSelectedBook();

			String[] options = {"Sim", "Não"};
			String newStatus = selectedBook.isRead() ? "não lido" : "lido";
			int res = JOptionPane.showOptionDialog(this,
				String.format("Deseja marcar o livro %s como %s?", selectedBook.getTitle(), newStatus), getTitle(),
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

			if (res != 0) return;

			bookController.updateReadStatus(selectedBook);
			updateView();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, String.format("Erro ao tentar alterar o status:\n%s", e.getMessage()),
				getTitle(), JOptionPane.ERROR_MESSAGE);
		}
	}//GEN-LAST:event_statusBtnActionPerformed

	private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
		try {
			var form = new BookForm(controllerFactory);
			showInternalFrame(form);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
				String.format("Erro ao tentar abrir o formulário de cadastro de livro:\n%s", e.getMessage()), getTitle(),
				JOptionPane.ERROR_MESSAGE);
		}
	}//GEN-LAST:event_newBtnActionPerformed

	private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
		try {
			var selectedBook = getSelectedBook();

			String[] options = {"Sim", "Não"};
			int res = JOptionPane.showOptionDialog(this,
				String.format("Tem certeza que deseja excluir o livro %s?\nNão é possível desfazer esta ação!",
					selectedBook.getTitle()),
				getTitle(), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

			if (res != 0) return;

			bookController.delete(selectedBook.getId());
			updateView();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, String.format("Erro ao tentar excluir:\n%s", e.getMessage()), getTitle(),
				JOptionPane.ERROR_MESSAGE);
		}
	}//GEN-LAST:event_deleteBtnActionPerformed

	private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
		try {
			updateView();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
				String.format("Erro ao tentar atualizar a tabela de livros:\n%s", e.getMessage()), getTitle(),
				JOptionPane.ERROR_MESSAGE);
			System.err.println(e);
			e.printStackTrace();
		}
	}//GEN-LAST:event_formInternalFrameActivated

	private void searchInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchInputKeyPressed
		updateView();
	}//GEN-LAST:event_searchInputKeyPressed

	private void filterComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterComboActionPerformed
		updateView();
	}//GEN-LAST:event_filterComboActionPerformed

	private void searchButtonaddBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchButtonaddBtnMouseEntered
		var comp = (JButton) evt.getComponent();
		setActiveBtn(comp);
	}//GEN-LAST:event_searchButtonaddBtnMouseEntered

	private void searchButtonaddBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchButtonaddBtnMouseExited
		var comp = (JButton) evt.getComponent();
		setInactiveBtn(comp);
	}//GEN-LAST:event_searchButtonaddBtnMouseExited

	private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
		updateView();
	}//GEN-LAST:event_searchButtonActionPerformed

	private void clearFilterButtonaddBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearFilterButtonaddBtnMouseEntered
		var comp = (JButton) evt.getComponent();
		setActiveBtn(comp);
	}//GEN-LAST:event_clearFilterButtonaddBtnMouseEntered

	private void clearFilterButtonaddBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearFilterButtonaddBtnMouseExited
		var comp = (JButton) evt.getComponent();
		setInactiveBtn(comp);
	}//GEN-LAST:event_clearFilterButtonaddBtnMouseExited

	private void clearFilterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFilterButtonActionPerformed
		searchInput.setText("");
		updateView();
	}//GEN-LAST:event_clearFilterButtonActionPerformed

	private void setActiveBtn(JButton btn) {
		btn.setForeground(Constants.BLUE);
	}

	private void setInactiveBtn(JButton btn) {
		btn.setForeground(Constants.FONT_COLOR);
	}

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTable bookTable;
  private javax.swing.JButton clearFilterButton;
  private javax.swing.JButton deleteBtn;
  private javax.swing.JButton detailsButton;
  private javax.swing.JButton editBtn;
  private javax.swing.JComboBox<models.filters.BookFilter> filterCombo;
  private javax.swing.JLabel filterLabel;
  private javax.swing.JButton newBtn;
  private javax.swing.JButton searchButton;
  private javax.swing.JTextField searchInput;
  private javax.swing.JLabel searchLabel;
  private javax.swing.JButton statusBtn;
  private javax.swing.JScrollPane tableScrollPane;
  private javax.swing.JLabel title;
  private javax.swing.JLabel totalLabel;
  // End of variables declaration//GEN-END:variables
}

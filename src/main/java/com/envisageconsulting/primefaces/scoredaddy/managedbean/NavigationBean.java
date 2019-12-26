package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.CompetitorDataSource;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationBean implements Serializable {

	@ManagedProperty("#{panelMenuBean}")
	private PanelMenuBean panelMenuBean;

	private static final long serialVersionUID = 1520318172495977648L;

	/**
	 * Redirect to login page.
	 * 
	 * @return Login page name.
	 */
	public String redirectToLogin() {
		return "/login.xhtml?faces-redirect=true";
	}

	/**
	 * Go to login page.
	 * 
	 * @return Login page name.
	 */
	public String toLogin() {
		return "/login.xhtml";
	}

	public String redirectToLayout() {
		collapseAllMenuItems();
		return "/secured/layout/layout.xhtml?faces-redirect=true";
	}

	public String redirectToAddAccount() {
		updateMenuItems();
		panelMenuBean.setAdminExpanded(true);
		return "/secured/layout/admin/addaccount.xhtml?faces-redirect=true";
	}

	public String redirectToAddUser() {
		updateMenuItems();
		panelMenuBean.setAdminExpanded(true);
		return "/secured/layout/admin/adduser.xhtml?faces-redirect=true";
	}

	public String redirectToAddTournament() {
		updateMenuItems();
		panelMenuBean.setTournamentExpanded(true);
		return "/secured/layout/tournament/add_tournament.xhtml?faces-redirect=true";
	}

	public String redirectToAddCompetition() {
		updateMenuItems();
		panelMenuBean.setCompetitionExpanded(true);
		return "/secured/layout/competition/add_competition.xhtml?faces-redirect=true";
	}

	public String redirectToAddCompetitor() {
		updateMenuItems();
		panelMenuBean.setCompetitorExpanded(true);
		return "/secured/layout/competitor/add_competitor.xhtml?faces-redirect=true";
	}

	public String redirectToRegisterCompetitor() {
		updateMenuItems();
		panelMenuBean.setCompetitorExpanded(true);
		return "/secured/layout/competitor/register_competitor.xhtml?faces-redirect=true";
	}

	public String redirectToAddFirearm() {
		updateMenuItems();
		panelMenuBean.setFirearmExpanded(true);
		return "/secured/layout/firearm/add_firearm.xhtml?faces-redirect=true";
	}

	public String redirectToGssfScoresheet() {
		updateMenuItems();
		panelMenuBean.setScoringExpanded(true);
		return "/secured/layout/gssf/indoor_scoresheet.xhtml?faces-redirect=true";
	}

	public String redirectToBullseyeScoresheet() {
		updateMenuItems();
		panelMenuBean.setBullseyeExpanded(true);
		return "/secured/layout/bullseye/bullseye_scoresheet.xhtml?faces-redirect=true";
	}

	public String redirectToViewBullseyeScores() {
		updateMenuItems();
		panelMenuBean.setResultsExpanded(true);
		return "/secured/layout/scores/view_scores_bullseye.xhtml?faces-redirect=true";
	}

	public String redirectToUpdateScores() {
		updateMenuItems();
		panelMenuBean.setScoringExpanded(true);
		return "/secured/layout/scores/update_scores.xhtml?faces-redirect=true";
	}

	public String redirectToViewGssfScores() {
		updateMenuItems();
		panelMenuBean.setResultsExpanded(true);
		return "/secured/layout/scores/view_scores.xhtml?faces-redirect=true";
	}

	public void updateMenuItems() {
		/*panelMenuBean.setAdminExpanded(panelMenuBean.isAdminExpanded());
		panelMenuBean.setTournamentExpanded(panelMenuBean.isTournamentExpanded());
		panelMenuBean.setCompetitionExpanded(panelMenuBean.isCompetitionExpanded());
		panelMenuBean.setCompetitorExpanded(panelMenuBean.isCompetitorExpanded());
		panelMenuBean.setFirearmExpanded(panelMenuBean.isFirearmExpanded());
		panelMenuBean.setScoringExpanded(panelMenuBean.isScoringExpanded());
		panelMenuBean.setBullseyeExpanded(panelMenuBean.isBullseyeExpanded());
		panelMenuBean.setResultsExpanded(panelMenuBean.isResultsExpanded());*/

		panelMenuBean.setAdminExpanded(false);
		panelMenuBean.setTournamentExpanded(false);
		panelMenuBean.setCompetitionExpanded(false);
		panelMenuBean.setCompetitorExpanded(false);
		panelMenuBean.setFirearmExpanded(false);
		panelMenuBean.setScoringExpanded(false);
		panelMenuBean.setBullseyeExpanded(false);
		panelMenuBean.setResultsExpanded(false);
	}

	public void collapseAllMenuItems() {
		panelMenuBean.setAdminExpanded(false);
		panelMenuBean.setTournamentExpanded(false);
		panelMenuBean.setCompetitionExpanded(false);
		panelMenuBean.setCompetitorExpanded(false);
		panelMenuBean.setFirearmExpanded(false);
		panelMenuBean.setScoringExpanded(false);
		panelMenuBean.setBullseyeExpanded(false);
		panelMenuBean.setResultsExpanded(false);
	}

	public PanelMenuBean getPanelMenuBean() {
		return panelMenuBean;
	}

	public void setPanelMenuBean(PanelMenuBean panelMenuBean) {
		this.panelMenuBean = panelMenuBean;
	}
}

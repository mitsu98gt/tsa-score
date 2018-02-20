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

	/**
	 * Redirect to info page.
	 * 
	 * @return Info page name.
	 */
	public String redirectToInfo() {
		return "/info.xhtml?faces-redirect=true";
	}

	/**
	 * Go to info page.
	 * 
	 * @return Info page name.
	 */
	public String toInfo() {
		return "/info.xhtml";
	}

	/**
	 * Redirect to welcome page.
	 * 
	 * @return Welcome page name.
	 */
	public String redirectToWelcome() {
		return "/secured/welcome.xhtml?faces-redirect=true";
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
		panelMenuBean.setGssfExpanded(true);
		return "/secured/layout/gssf/indoor_scoresheet.xhtml?faces-redirect=true";
	}

	public String redirectToBullseyeScoresheet() {
		updateMenuItems();
		panelMenuBean.setBullseyeExpanded(true);
		return "/secured/layout/bullseye/bullseye_scoresheet.xhtml?faces-redirect=true";
	}

	public String redirectToViewGssfScores() {
		updateMenuItems();
		panelMenuBean.setScoresExpanded(true);
		return "/secured/layout/scores/view_scores.xhtml?faces-redirect=true";
	}

	public String redirectToViewBullseyeScores() {
		updateMenuItems();
		panelMenuBean.setScoresExpanded(true);
		return "/secured/layout/scores/view_scores_bullseye.xhtml?faces-redirect=true";
	}

	public String redirectToUpdateScores() {
		updateMenuItems();
		panelMenuBean.setScoresExpanded(true);
		return "/secured/layout/scores/update_scores.xhtml?faces-redirect=true";
	}

	public void updateMenuItems() {
		panelMenuBean.setAdminExpanded(panelMenuBean.isAdminExpanded());
		panelMenuBean.setTournamentExpanded(panelMenuBean.isTournamentExpanded());
		panelMenuBean.setCompetitionExpanded(panelMenuBean.isCompetitionExpanded());
		panelMenuBean.setCompetitorExpanded(panelMenuBean.isCompetitorExpanded());
		panelMenuBean.setFirearmExpanded(panelMenuBean.isFirearmExpanded());
		panelMenuBean.setGssfExpanded(panelMenuBean.isGssfExpanded());
		panelMenuBean.setBullseyeExpanded(panelMenuBean.isBullseyeExpanded());
		panelMenuBean.setScoresExpanded(panelMenuBean.isScoresExpanded());
	}

	public void collapseAllMenuItems() {
		panelMenuBean.setAdminExpanded(false);
		panelMenuBean.setTournamentExpanded(false);
		panelMenuBean.setCompetitionExpanded(false);
		panelMenuBean.setCompetitorExpanded(false);
		panelMenuBean.setFirearmExpanded(false);
		panelMenuBean.setGssfExpanded(false);
		panelMenuBean.setBullseyeExpanded(false);
		panelMenuBean.setScoresExpanded(false);
	}


	/**
	 * Go to welcome page.
	 *
	 * @return Welcome page name.
	 */
	public String toWelcome() {
		return "/secured/welcome.xhtml";
	}

	public PanelMenuBean getPanelMenuBean() {
		return panelMenuBean;
	}

	public void setPanelMenuBean(PanelMenuBean panelMenuBean) {
		this.panelMenuBean = panelMenuBean;
	}
}
